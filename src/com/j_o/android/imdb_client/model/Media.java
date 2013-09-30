package com.j_o.android.imdb_client.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {

	private String imdbId;
	private String title;
	private String genre;
	private String actors;
	private String director;
	private String duraction;
	private String plot;
	private Bitmap poster;

	public static final Parcelable.Creator<Media> CREATOR = new Creator<Media>() {

		@Override
		public Media createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Media(source);
		}

		@Override
		public Media[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Media[size];
		}
	};

	public Media() {
		super();
		this.imdbId = "";
		this.title = "";
		this.genre = "";
		this.actors = "";
		this.director = "";
		this.duraction = "";
		this.plot = "";
		this.poster = null;
	}

	public Media(Parcel in) {
		readFromParcel(in);
	}

	public Media(String imdbId, String title) {
		super();
		this.imdbId = imdbId;
		this.title = title;
	}

	public Media(String imdbId, String title, String genre, String actors,
			String director, String duraction, String plot, Bitmap poster) {
		super();
		this.imdbId = imdbId;
		this.title = title;
		this.genre = genre;
		this.actors = actors;
		this.director = director;
		this.duraction = duraction;
		this.plot = plot;
		this.poster = poster;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getActors() {
		return actors;
	}

	public String getDirector() {
		return director;
	}

	public String getDuraction() {
		return duraction;
	}

	public String getGenre() {
		return genre;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String getPlot() {
		return plot;
	}

	public Bitmap getPoster() {
		return poster;
	}

	public String getTitle() {
		return title;
	}

	private void readFromParcel(Parcel in) {

		this.imdbId = in.readString();
		this.title = in.readString();
		this.genre = in.readString();
		this.actors = in.readString();
		this.director = in.readString();
		this.duraction = in.readString();
		this.plot = in.readString();
		// this.poster = in.readParcelable(Bitmap.class.getClassLoader());
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setDuraction(String duraction) {
		this.duraction = duraction;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public void setPoster(Bitmap poster) {
		this.poster = poster;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(imdbId);
		dest.writeString(title);
		dest.writeString(genre);
		dest.writeString(actors);
		dest.writeString(director);
		dest.writeString(duraction);
		dest.writeString(plot);
		// dest.writeValue(poster);
	}

}
