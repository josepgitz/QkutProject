package ke.co.qkut.qkut.Modeler;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Data {
    private @Expose List<Organisation> data;

    public List<Organisation> getOrganisations() {
        return data;
    }

    public void setOrganisations(List<Organisation> data) {
        this.data = data;
    }
}
