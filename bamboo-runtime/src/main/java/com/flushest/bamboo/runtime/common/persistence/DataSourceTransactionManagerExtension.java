package com.flushest.bamboo.runtime.common.persistence;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/1 0001.
 */
@Component("transactionManager")
public class DataSourceTransactionManagerExtension extends DataSourceTransactionManager {

    @Override
    public void afterPropertiesSet() {
        Collection<DataSource> dataSources = SqlSessionFactoryProxy.getDataSourceMap().values();
        setDataSource(dataSources.iterator().next());
        super.afterPropertiesSet();
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        TransactionInfoUtil.clearStatus();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        super.doRollback(status);
        TransactionInfoUtil.clearStatus();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        super.doCommit(status);
        TransactionInfoUtil.clearStatus();
    }
}
