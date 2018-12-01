package com.example.indus.nytimes.modeldto;

import com.example.indus.nytimes.database.NewsTypeConverter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "newstables")
@TypeConverters(NewsTypeConverter.class)
public class NewsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("section")
    private String section;

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("multimedia")
    private List<MultimediaItem> multimedia;

    @SerializedName("published_date")
    private String publishedDate;

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MultimediaItem> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaItem> multimedia) {
        this.multimedia = multimedia;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
