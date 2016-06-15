package in.codekamp.codekampconnect.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Himanshu on 6/13/2016.
 */
public class DataAction {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("contact_id")
    @Expose
    private Integer contactId;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("email")
    @Expose
    private AnEmail email;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The contactId
     */
    public Integer getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId
     * The contact_id
     */
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The email
     */
    public AnEmail getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(AnEmail email) {
        this.email = email;
    }

}