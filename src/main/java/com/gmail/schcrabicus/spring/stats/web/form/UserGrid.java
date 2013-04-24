package com.gmail.schcrabicus.spring.stats.web.form;

import com.gmail.schcrabicus.spring.stats.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 15.04.13
 * Time: 7:44
 * To change this template use File | Settings | File Templates.
 */
public class UserGrid implements Serializable {

    private int totalPages;

    private int currentPage;

    private Long totalRecords;

    private List<User> users;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords( Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
