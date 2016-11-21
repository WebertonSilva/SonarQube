package org.glassfish.movieplex7.batch;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.glassfish.movieplex7.entities.Sales;

@Named
@RequestScoped
public class SalesBean {

    @PersistenceContext
    EntityManager entityManager;
    
    private static final Logger LOGGER = Logger.getLogger(SalesBean.class.getName());    

    public void runJob() {
        try {
            JobOperator job = BatchRuntime.getJobOperator();
            long jobId = job.start("eod-sales", new Properties());
            LOGGER.info("Started job with id: " + jobId);
        } catch (JobStartException ex) {
        	LOGGER.log(Level.SEVERE, "Falha ao iniciar o serviço", ex);
        }
    }

    public List<Sales> getSalesData() {
        return entityManager.createNamedQuery("Sales.findAll", Sales.class)
                .getResultList();
    }
}
