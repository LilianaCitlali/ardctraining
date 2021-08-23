package org.training.core.job;

import com.training.core.model.CustomProductLabelModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelRemovalException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.training.core.customer.service.CustomProductLabelService;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class CustomProductLabelCleanupJobPerformable extends AbstractJobPerformable<CronJobModel> {

    private TimeService timeService;
    private CustomProductLabelService customProductLabelService;
    private ModelService modelService;

    private static final Logger LOG =LoggerFactory.getLogger(CustomProductLabelCleanupJobPerformable.class);

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        final Date now = getTimeService().getCurrentTime();
        //generar método en el service-> dao: de los registros cuyo expireDAte esté en el pasado
        final List<CustomProductLabelModel> labels = getCustomProductLabelService().findByExpireDate(now);

        if(CollectionUtils.isNotEmpty(labels)){
            LOG.info(String.format("removing %s product labels", labels.size()));

            try {
                getModelService().removeAll(labels);
            } catch (final ModelRemovalException ex){
                return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
            }
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    public CustomProductLabelService getCustomProductLabelService() {
        return customProductLabelService;
    }

    public void setCustomProductLabelService(CustomProductLabelService customProductLabelService) {
        this.customProductLabelService = customProductLabelService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
