package com.gmail.schcrabicus.spring.stats.web.controller;

import com.gmail.schcrabicus.spring.stats.domain.User;
import com.gmail.schcrabicus.spring.stats.service.IUserService;
import com.gmail.schcrabicus.spring.stats.web.form.UserGrid;
import com.gmail.schcrabicus.spring.stats.web.form.Message;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 09.04.13
 * Time: 9:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping( value = "/users")
public class UserController {

    private static Logger log = Logger.getLogger( UserController.class );

    @Autowired
    @Qualifier( "userServiceImpl" )
    private IUserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping( value = { "" , "/" , "/list" } , method = RequestMethod.GET )
    public String list(){
        log.debug("listing all users...");
        return "users/list";
    }

    @RequestMapping( value = "/listgrid" , method = RequestMethod.GET , produces = "application/json")
    @ResponseBody
    public UserGrid listPage( @RequestParam( value = "page" , required = false ) Integer page,
                                 @RequestParam( value = "rows" , required = false ) Integer rows ){
        log.debug("retieving page content...");
        PageRequest pageRequest = new PageRequest( page - 1 , rows );

        Page<User> userPage = userService.findAllByPage( pageRequest );

        UserGrid userGrid = new UserGrid();
        userGrid.setCurrentPage( userPage.getNumber() + 1 );
        userGrid.setTotalPages( userPage.getTotalPages() );
        userGrid.setTotalRecords( userPage.getTotalElements() );
        userGrid.setUsers(Lists.newArrayList( userPage.iterator() ));

        return userGrid;
    }

    @RequestMapping( method = RequestMethod.POST )
    public String create( @Valid User user ,
                            BindingResult bindingResult,
                            Model uiModel,
                            RedirectAttributes redirectAttributes,
                            Locale locale,
                            @RequestParam( value = "file" , required = false ) Part file ){

        if ( bindingResult.hasErrors() ){
            uiModel.addAttribute( "user" , user );
            uiModel.addAttribute( "message",
                    new Message( "error " ,
                            messageSource.getMessage( "user_save_fail" , new Object[]{} , locale )
                    )
            );
            return "users/create";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute( "message" ,
                new Message( "success" ,
                        messageSource.getMessage( "user_save_success" , new Object[]{} , locale )
                )
        );

        if ( file != null ){
            InputStream inputStream;
            byte[] photo = null;

            try{
                inputStream = file.getInputStream();
                if ( inputStream != null ){
                    photo = IOUtils.toByteArray( inputStream );
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            user.setPhoto( photo );
        }

        userService.create( user );

        return "redirect:/users/" + user.getId().toString();
    }
}
