package com.example.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_API_URI="https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY="q";
    public static final String KEY="key";
    public static final String API_KEY=""; //get an API key for books from google and paste here
    public static URL buildUrl(String title){

        URL url=null;
        Uri uri=Uri.parse(BASE_API_URI).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY,title)
                //.appendQueryParameter(KEY,API_KEY)//Add the AI Key as a parameter to build the URi
                .build();

        try {
            url = new URL(uri.toString());
            }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }
     return url;
    }
    public static String getJson(URL url) throws IOException {

        HttpURLConnection connection =(HttpURLConnection) url.openConnection();

        try {
            InputStream stream=connection.getInputStream();
            Scanner scanner=new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData=scanner.hasNext();
            if (hasData){
                return scanner.next();
            }

            else {
                return null;
            }
        }
        catch (Exception e){
            Log.d("Error",e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }
    public static ArrayList<Book> getBooksFromJson (String json){

    final String ID ="id";
    final String TITLE="title";
    final String SUBTITLE="subtitle";
    final String AUTHORS="authors";
    final String PUBLISHER="publisher";
    final String PUBLISHED_DATE="publishedDate";
    final String ITEMS="items";
    final String VOLUMEINFO="volumeInfo";
    final String DESCPRIPTION="description";
    final String THUMBNAIL="thumbnail";
    final String IMAGELINKS="imageLinks";
    ArrayList<Book> books=new ArrayList<Book>();


    try {
        JSONObject jsonBooks=new JSONObject(json);
        JSONArray arrayBooks=jsonBooks.getJSONArray(ITEMS);
        int numberOfBooks=arrayBooks.length();
        for(int i=0;i<numberOfBooks;i++){
            JSONObject bookJson= arrayBooks.getJSONObject(i);
            JSONObject volumeInfoJSON= bookJson.getJSONObject(VOLUMEINFO);
            JSONObject imageLinksJSON=volumeInfoJSON.getJSONObject(IMAGELINKS);
            int authorNum=volumeInfoJSON.getJSONArray(AUTHORS).length();
            String [] authors= new String[authorNum];
            for(int j=0;j<authorNum;j++){
                authors[j]=volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
            }
            Book book=new Book(
                    bookJson.getString(ID),
                    volumeInfoJSON.getString(TITLE),
                    (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE)),
                    authors,
                    volumeInfoJSON.getString(PUBLISHER),
                    volumeInfoJSON.getString(PUBLISHED_DATE),
                    volumeInfoJSON.getString(DESCPRIPTION),
                    imageLinksJSON.getString(THUMBNAIL)
                               );
            books.add(book);
        }
    }
    catch (JSONException e){
        e.printStackTrace();
    }

        return books;
    }
}
