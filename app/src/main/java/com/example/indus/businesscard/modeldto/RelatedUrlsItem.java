package com.example.indus.businesscard.modeldto;

import com.google.gson.annotations.SerializedName;

public class RelatedUrlsItem{

	@SerializedName("suggested_link_text")
	private String suggestedLinkText;

	@SerializedName("url")
	private String url;

	public void setSuggestedLinkText(String suggestedLinkText){
		this.suggestedLinkText = suggestedLinkText;
	}

	public String getSuggestedLinkText(){
		return suggestedLinkText;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"RelatedUrlsItem{" + 
			"suggested_link_text = '" + suggestedLinkText + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}