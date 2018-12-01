package com.example.indus.nytimes.modeldto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItem {

    @SerializedName("per_facet")
    private List<String> perFacet;

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("item_type")
    private String itemType;

    @SerializedName("org_facet")
    private List<String> orgFacet;

    @SerializedName("section")
    private String section;

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("related_urls")
    private List<RelatedUrlsItem> relatedUrls;

    @SerializedName("title")
    private String title;

    @SerializedName("des_facet")
    private List<String> desFacet;

    @SerializedName("url")
    private String url;

    @SerializedName("short_url")
    private String shortUrl;

    @SerializedName("material_type_facet")
    private String materialTypeFacet;

    @SerializedName("thumbnail_standard")
    private String thumbnailStandard;

    @SerializedName("multimedia")
    private List<MultimediaItem> multimedia;

    @SerializedName("geo_facet")
    private List<String> geoFacet;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("byline")
    private String byline;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("kicker")
    private String kicker;

    public void setPerFacet(List<String> perFacet) {
        this.perFacet = perFacet;
    }

    public List<String> getPerFacet() {
        return perFacet;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setOrgFacet(List<String> orgFacet) {
        this.orgFacet = orgFacet;
    }

    public List<String> getOrgFacet() {
        return orgFacet;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setRelatedUrls(List<RelatedUrlsItem> relatedUrls) {
        this.relatedUrls = relatedUrls;
    }

    public List<RelatedUrlsItem> getRelatedUrls() {
        return relatedUrls;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDesFacet(List<String> desFacet) {
        this.desFacet = desFacet;
    }

    public List<String> getDesFacet() {
        return desFacet;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setMaterialTypeFacet(String materialTypeFacet) {
        this.materialTypeFacet = materialTypeFacet;
    }

    public String getMaterialTypeFacet() {
        return materialTypeFacet;
    }

    public void setThumbnailStandard(String thumbnailStandard) {
        this.thumbnailStandard = thumbnailStandard;
    }

    public String getThumbnailStandard() {
        return thumbnailStandard;
    }

    public void setMultimedia(List<MultimediaItem> multimedia) {
        this.multimedia = multimedia;
    }

    public List<MultimediaItem> getMultimedia() {
        return multimedia;
    }

    public void setGeoFacet(List<String> geoFacet) {
        this.geoFacet = geoFacet;
    }

    public List<String> getGeoFacet() {
        return geoFacet;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getByline() {
        return byline;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }

    public String getKicker() {
        return kicker;
    }

    @Override
    public String toString() {
        return
                "NewsItem{" +
                        "per_facet = '" + perFacet + '\'' +
                        ",subsection = '" + subsection + '\'' +
                        ",item_type = '" + itemType + '\'' +
                        ",org_facet = '" + orgFacet + '\'' +
                        ",section = '" + section + '\'' +
                        ",abstract = '" + previewText + '\'' +
                        ",related_urls = '" + relatedUrls + '\'' +
                        ",title = '" + title + '\'' +
                        ",des_facet = '" + desFacet + '\'' +
                        ",url = '" + url + '\'' +
                        ",short_url = '" + shortUrl + '\'' +
                        ",material_type_facet = '" + materialTypeFacet + '\'' +
                        ",thumbnail_standard = '" + thumbnailStandard + '\'' +
                        ",multimedia = '" + multimedia + '\'' +
                        ",geo_facet = '" + geoFacet + '\'' +
                        ",updated_date = '" + updatedDate + '\'' +
                        ",created_date = '" + createdDate + '\'' +
                        ",byline = '" + byline + '\'' +
                        ",published_date = '" + publishedDate + '\'' +
                        ",kicker = '" + kicker + '\'' +
                        "}";
    }
}