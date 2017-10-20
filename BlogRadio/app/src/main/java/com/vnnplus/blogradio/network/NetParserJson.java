package com.vnnplus.blogradio.network;

import android.util.Log;

import com.vnnplus.blogradio.model.Category;
import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.model.InfoUser;
import com.vnnplus.blogradio.model.ItemMenu;
import com.vnnplus.blogradio.model.Package;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Signers;
import com.vnnplus.blogradio.model.Slider;
import com.vnnplus.blogradio.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/10/2017.
 */

public class NetParserJson {
    public static ArrayList<Slider> parserSlider(String json){
        try {
            ArrayList<Slider> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i =0 ; i< jsonArray.length() ; i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Slider slider = new Slider();
                slider.setImgUrl(object.getString("avatar"));
                slider.setName(object.getString("name"));
                slider.setSlugname(object.getString("slugname"));
                slider.setType(object.getString("type"));
                slider.setId(object.getInt("id"));
                slider.setMediaUrl(object.getString("mediaUrl"));
                JSONObject jsonObject1 = object.getJSONObject("category");
                Signers signers = new Signers();
                signers.setName(jsonObject1.getString("name"));
                signers.setId(jsonObject1.getInt("id"));
                slider.setSigners(signers);
                list.add(slider);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Radio> parserListRadio(String json,boolean isDetail){
        ArrayList<Radio> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i =0 ; i<jsonArray.length() ; i++){
                JSONObject object  = jsonArray.getJSONObject(i);
                Radio radio = new Radio();
                radio.setName(object.getString("name"));
                radio.setAvatar(object.getString("avatar"));
                radio.setDescription(object.getString("description"));
                radio.setId(object.getInt("id"));
                radio.setPublistdate(object.getString("publishdate"));
                radio.setView(object.getInt("view"));
                if (!isDetail){
                    radio.setSort(object.getString("sort"));
                }
                if (isDetail){
                    radio.setContent(object.getString("content"));
                    radio.setMediaurl(object.getString("media_url"));
                }
                else
                    radio.setMediaurl(object.getString("mediaurl"));
                JSONArray array = object.getJSONArray("singers");
                ArrayList<Signers> signersArrayList = new ArrayList<>();
                for (int j = 0 ;j<array.length() ; j++){
                    JSONObject object1 = array.getJSONObject(j);
                    Signers signers = new Signers();
                    signers.setId(object1.getInt("id"));
                    signers.setAvatar(object1.getString("avatar"));
                    signers.setName(object1.getString("name"));
                    signersArrayList.add(signers);
                }
//                if (!isDetail){
                    JSONObject jsonObject1 = object.getJSONObject("category");
                if (jsonObject1!=null){
                    Signers signers = new Signers();
                    signers.setId(jsonObject1.getInt("id"));
                    signers.setName(jsonObject1.getString("name"));
                    radio.setSigners(signers);
                }

//                }
                if (isDetail){
                    ArrayList<Signers> memberArrayList = new ArrayList<>();
                    JSONArray array1 = object.getJSONArray("members");
                    for (int k = 0;k<array1.length();k++){
                        JSONObject object1 = array1.getJSONObject(k);
                        Signers signers = new Signers();
                        signers.setId(object1.getInt("id"));
                        signers.setAvatar(object1.getString("avatar"));
                        signers.setName(object1.getString("name"));
                        memberArrayList.add(signers);
                    }
                    radio.setlMember(memberArrayList);
                }
                radio.setlSingler(signersArrayList);
                list.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<Category> parserListCategory(String json){
        ArrayList<Category> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i<jsonArray.length() ; i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Category category = new Category();
                category.setName(object.getString("name"));
                category.setId(object.getInt("id"));
                category.setSort(object.getString("sort"));
                category.setParentID(object.getString("parentId"));
                category.setAvatar(object.getString("avatar"));
                if (category.getAvatar()!= null && !category.getAvatar().equals("") && !category.getAvatar().equals("null"))
                    list.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void parserMenuItem(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("audio");
            for (int i =0 ; i< jsonArray.length() ; i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                ItemMenu itemMenu = new ItemMenu();
                itemMenu.setId(jsonObject2.getInt("id"));
                itemMenu.setName(jsonObject2.getString("name"));
                Util.lRadio.add(itemMenu);
            }

            JSONArray jsonArray1 = jsonObject1.getJSONArray("video");
            for (int i =0 ; i< jsonArray1.length() ; i++){
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                ItemMenu itemMenu = new ItemMenu();
                itemMenu.setId(jsonObject2.getInt("id"));
                itemMenu.setName(jsonObject2.getString("name"));
                Util.lVlog.add(itemMenu);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Comment> parserListComment(String json){
        ArrayList<Comment> lComment = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            if (code == 0)
            {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i =0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Comment comment = new Comment();
                    comment.setComment(jsonObject1.getString("comment"));
                    comment.setMsisdn(jsonObject1.getString("msisdn"));
                    comment.setAvatar(jsonObject1.getString("avatar"));
                    comment.setNickname(jsonObject1.getString("nickname"));
                    comment.setTime(jsonObject1.getString("time"));
                    lComment.add(comment);
                }
            }
            else {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lComment;
    }
    public static InfoUser parserInfoUser(String json){
        InfoUser infoUser = new InfoUser();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            infoUser.setAvatar(jsonObject1.getString("avatar"));
            infoUser.setEmail(jsonObject1.getString("email"));
            infoUser.setNickName(jsonObject1.getString("nickname"));
            infoUser.setMsisdn(jsonObject1.getString("msisdn"));
            infoUser.setDate(jsonObject1.getString("birthday"));
            infoUser.setGenDer(jsonObject1.getInt("gender"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoUser;
    }
    public static ArrayList<Package> parserInfoRegister(String json){
        ArrayList<Package> list = new ArrayList<>();
        Package radio = new Package();
        Package vlog = new Package();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject object = jsonObject.getJSONObject("data");
            String smsNumber = object.getString("smsNumber");
            radio.setSmsNumber(smsNumber);
            vlog.setSmsNumber(smsNumber);
            JSONArray jsonArray = object.getJSONArray("package");
            radio.setName(String.valueOf(jsonArray.get(0)));
            vlog.setName(String.valueOf(jsonArray.get(1)));
            JSONObject price = object.getJSONObject("prices");
            radio.setPrices(price.getString("RA"));
            vlog.setPrices(price.getString("VL"));
            list.add(radio);
            list.add(vlog);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void parserStatus(String json){
        try {
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> list1 = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
//            String data = jsonObject.getString("data");
            int code = jsonObject.getInt("code");
            if (code!=0){

            }
            else {

                if (Util.lPackage.size()> 0)
                    Util.lPackage.clear();
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                JSONArray jsonArray = jsonObject1.getJSONArray("active");
                for (int i =0 ; i< jsonArray.length() ; i++){
                    list1.add(String.valueOf(jsonArray.get(i)));
                }
                JSONArray jsonArray1 = jsonObject1.getJSONArray("allReg");
                for (int i =0 ; i < jsonArray1.length() ; i++){
                    list.add(String.valueOf(jsonArray1.get(i)));
                }

                for (int i =0 ; i<list.size();i++){
                    if (list.get(i).equals("RADIO") || list.get(i).equals("VLOG"))
                        Util.lPackage.add(list.get(i));
                }
                for (int i = 0 ; i < list1.size() ; i++){
                    if (list1.get(i).equals("RADIO") || list1.get(i).equals("VLOG"))
                        Util.lAllReg.add(list1.get(i));
                }
            }
            Log.e("lPackage" , Util.lPackage.size() + "");
            Log.e("lAllReg" , Util.lAllReg.size() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Radio> parserlSearch(String json , boolean isRadio){
        ArrayList<Radio> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject object = jsonObject.getJSONObject("data");
            JSONArray array;
            if (isRadio)
                array = object.getJSONArray("radio");
            else
                array = object.getJSONArray("vlog");
            for (int i =0 ;i< array.length() ; i++){
                JSONObject jsonObject1 = array.getJSONObject(i);
                Radio radio = new Radio();
                radio.setId(jsonObject1.getInt("blogid"));
                radio.setName(jsonObject1.getString("name"));
                radio.setMediaurl(jsonObject1.getString("mediaurl"));
                radio.setAvatar(jsonObject1.getString("avatar"));

                JSONArray array1 = jsonObject1.getJSONArray("singers");
                ArrayList<Signers> signersArrayList = new ArrayList<>();
                for (int j = 0 ;j<array1.length() ; j++){
                    JSONObject object1 = array1.getJSONObject(j);
                    Signers signers = new Signers();
                    signers.setId(object1.getInt("id"));
                    signers.setAvatar(object1.getString("avatar"));
                    signers.setName(object1.getString("name"));
                    signersArrayList.add(signers);
                }
                radio.setlSingler(signersArrayList);
//                if (!isDetail){
                JSONObject jsonObject2 = jsonObject1.getJSONObject("category");
                if (jsonObject2!=null){
                    Signers signers = new Signers();
                    signers.setId(jsonObject2.getInt("id"));
                    signers.setName(jsonObject2.getString("name"));
                    radio.setSigners(signers);
                }

                list.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<Radio> parserlFavorite(String json){
        ArrayList<Radio> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            if (data.equals("null")){

            }
            else {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0 ; i< jsonArray.length() ; i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Radio radio = new Radio();
                    radio.setId(object.getInt("blogid"));
                    radio.setName(object.getString("name"));
                    radio.setAvatar(object.getString("avatar"));
                    radio.setMediaurl(object.getString("mediaurl"));
                    radio.setView(object.getInt("view"));
                    radio.setContent(object.getString("description"));
                    radio.setPublistdate(object.getString("publishdate"));
                    if (object.getString("type").equals("audio"))
                        radio.setVlog(false);
                    else
                        radio.setVlog(true);
                    if (object.has("singers")){
                        JSONArray array = object.getJSONArray("singers");
                        ArrayList<Signers> signersArrayList = new ArrayList<>();
                        for (int j = 0 ;j<array.length() ; j++){
                            JSONObject object1 = array.getJSONObject(j);
                            Signers signers = new Signers();
                            signers.setId(object1.getInt("id"));
                            signers.setAvatar(object1.getString("avatar"));
                            signers.setName(object1.getString("name"));
                            signersArrayList.add(signers);
                        }
                        radio.setlSingler(signersArrayList);
                    }

                    JSONObject jsonObject1 = object.getJSONObject("category");
                    Signers signers = new Signers();
                    signers.setId(jsonObject1.getInt("id"));
                    signers.setName(jsonObject1.getString("name"));
                    radio.setSigners(signers);
                    list.add(radio);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<Radio> parserListSameVoice(String json){
        ArrayList<Radio> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0 ; i< jsonArray.length() ; i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Radio radio = new Radio();
                radio.setId(object.getInt("id"));
                radio.setName(object.getString("name"));
                radio.setAvatar(object.getString("avatar"));
                radio.setMediaurl(object.getString("mediaurl"));
                radio.setView(object.getInt("view"));
                radio.setContent(object.getString("description"));
                if (object.getString("type").equals("audio"))
                    radio.setVlog(false);
                else
                    radio.setVlog(true);
                JSONObject jsonObject1 = object.getJSONObject("category");
                Signers signers = new Signers();
                signers.setId(jsonObject1.getInt("id"));
                signers.setName(jsonObject1.getString("name"));
                radio.setSigners(signers);
                list.add(radio);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
