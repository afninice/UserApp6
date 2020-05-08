package afniramadania.tech.usergithubapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<SearchDataModel> items;

    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }

    public int getTotalCount(){
        return totalCount;
    }

    public void setIncompleteResults(boolean incompleteResults){
        this.incompleteResults = incompleteResults;
    }

    public boolean isIncompleteResults(){
        return incompleteResults;
    }

    public void setItems(List<SearchDataModel> items){
        this.items = items;
    }

    public List<SearchDataModel> getItems(){
        return items;
    }

    @Override
    public String toString(){
        return
                "SearchResponse{" +
                        "total_count = '" + totalCount + '\'' +
                        ",incomplete_results = '" + incompleteResults + '\'' +
                        ",items = '" + items + '\'' +
                        "}";
    }


}
