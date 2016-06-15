package in.codekamp.codekampconnect.services.CodeKamp;

import in.codekamp.codekampconnect.models.Error;

public interface Callback<T> {

    void onSuccess(T response);


    void onFailure(Error error);
}
