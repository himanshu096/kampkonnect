package in.codekamp.codekampconnect.services.CodeKamp;

import in.codekamp.codekampconnect.models.Batch;
import in.codekamp.codekampconnect.models.BatchData;
import in.codekamp.codekampconnect.models.Contact;
import in.codekamp.codekampconnect.models.DataAction;
import in.codekamp.codekampconnect.models.ItemResponse;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.models.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cerebro on 22/04/16.
 */
public interface CodeKampServiceInterface {

    @GET("authenticate")
    Call<Token> login(@Query("email") String email, @Query("password") String password);


    @GET("contacts")
    Call<ListResponse<Contact>> listContacts(@Header("Authorization") String accessToken, @Query("page") int page);

    @GET("contacts/{id}")
    Call<ItemResponse<Contact>> getContact(@Header("Authorization") String Token, @Path("id") int contactId);


    @FormUrlEncoded
    @PUT("contacts/{id}")
    Call<ItemResponse<Contact>> updateContact(@Header("Authorization")String Token,@Path("id") String contactId, @Field("first_name") String FirstName, @Field("last_name") String LastName,@Field("birthday") String bday,@Field("facebook_id") String facebook,@Field("linkedin_id") String linkedin);


    @FormUrlEncoded
    @POST("contacts")
    Call<ItemResponse<Contact>> createContact(@Header("Authorization") String Token, @Field("first_name") String FirstName, @Field("last_name") String LastName,@Field("phone_numbers[0]") String PhoneNumbers, @Field("emails[0]") String email,@Field("birthday") String bday,@Field("facebook_id") String facebook,@Field("linkedin_id") String linkedin);


    @GET("actions")
    Call<ListResponse<DataAction>> listactions(@Header("Authorization") String accessToken, @Query("type") String type);

    @GET("batches/")
    Call<ListResponse<Batch>> listbatches(@Header("Authorization") String accessToken, @Query("page") int page);


}
