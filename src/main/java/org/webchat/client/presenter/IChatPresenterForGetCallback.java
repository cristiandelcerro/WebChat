package org.webchat.client.presenter;

/**
 * Created with IntelliJ IDEA.
 * User: cristianbq
 * Date: 5/12/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public interface IChatPresenterForGetCallback {
    public void failedReception();
    public void processGetResponse(String getResponse);
}
