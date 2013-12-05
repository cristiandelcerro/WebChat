package org.webchat.client.model;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface ServerResponseAutoBeanFactory extends AutoBeanFactory {
    public AutoBean<IServerResponse> serverResponse();
    public AutoBean<IServerResponse> serverResponse(IServerResponse toWrap);
}
