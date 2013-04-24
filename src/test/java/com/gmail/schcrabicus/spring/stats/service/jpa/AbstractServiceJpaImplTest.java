package com.gmail.schcrabicus.spring.stats.service.jpa;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: schcrabicus
 * Date: 22.04.13
 * Time: 7:56
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = {"classpath*:**/spring-test-root-config.xml"})/*( locations = {"classpath*:**//*spring-test-root-config.xml"})*/
/*@ImportResource(value = "classpath*:**//*spring-test-datasource.xml")*/
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true )
/*@ActiveProfiles( { "test,mysql" })*/
public abstract class AbstractServiceJpaImplTest/* extends AbstractTransactionalJUnit4SpringContextTests*/ {
}
