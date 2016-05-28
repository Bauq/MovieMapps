package moviemapps.gr12.compumovil.udea.edu.co.moviemapps.rest;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moviemapps.gr12.compumovil.udea.edu.co.moviemapps.model.Movie;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "results",
        "total_pages",
        "total_results"
})
/**
 * Created by SA on 12/04/2016.
 */
public class Resultado {

    @JsonProperty("page")
    private Integer page;
    @JsonProperty("results")
    private List<Movie> results = new ArrayList<Movie>();
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_results")
    private Integer totalResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The page
     */
    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    /**
     * @return The results
     */
    @JsonProperty("results")
    public List<Movie> getResults() {
        return results;
    }
    



}
