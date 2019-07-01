
package org.open.budget.costlocator.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

@ToString
public class ContactPoint {

    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("faxNumber")
    @Expose
    private String faxNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public String getTelephone() {
        return telephone;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
