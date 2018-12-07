package com.himer.android.modle;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class SearchSound {

    public String play_path_64;

    @NotNull
    public long saveTime;

    @Id
    public long id;
    public String title;
    public String tags;
    public String play_path;
    public boolean is_public;
    public long created_at;
    public int category_id;
    public String cover_path;
    public int user_source;
    public double duration;
    public long updated_at;
    public String play_path_32;
    public String download_path;
    public int upload_source;
    public String album_cover_path;
    public int album_id;
    public String album_title;
    public long uid;
    public boolean is_v;
    public String nickname;
    public String avatar_path;
    //	public List<String> all_track;
    public int count_play;
    public int count_comment;
    public int count_share;
    public int count_like;
    public String smallLogo;
    public boolean is_like;
    public boolean is_playing;
    public boolean isRelay;
    public String play_path_aac_v224;

    @Generated(hash = 1916622752)
    public SearchSound(String play_path_64, long saveTime, long id, String title,
                       String tags, String play_path, boolean is_public, long created_at,
                       int category_id, String cover_path, int user_source, double duration,
                       long updated_at, String play_path_32, String download_path,
                       int upload_source, String album_cover_path, int album_id,
                       String album_title, long uid, boolean is_v, String nickname,
                       String avatar_path, int count_play, int count_comment, int count_share,
                       int count_like, String smallLogo, boolean is_like, boolean is_playing,
                       boolean isRelay, String play_path_aac_v224) {
        this.play_path_64 = play_path_64;
        this.saveTime = saveTime;
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.play_path = play_path;
        this.is_public = is_public;
        this.created_at = created_at;
        this.category_id = category_id;
        this.cover_path = cover_path;
        this.user_source = user_source;
        this.duration = duration;
        this.updated_at = updated_at;
        this.play_path_32 = play_path_32;
        this.download_path = download_path;
        this.upload_source = upload_source;
        this.album_cover_path = album_cover_path;
        this.album_id = album_id;
        this.album_title = album_title;
        this.uid = uid;
        this.is_v = is_v;
        this.nickname = nickname;
        this.avatar_path = avatar_path;
        this.count_play = count_play;
        this.count_comment = count_comment;
        this.count_share = count_share;
        this.count_like = count_like;
        this.smallLogo = smallLogo;
        this.is_like = is_like;
        this.is_playing = is_playing;
        this.isRelay = isRelay;
        this.play_path_aac_v224 = play_path_aac_v224;
    }

    @Generated(hash = 15847617)
    public SearchSound() {
    }

    public String getPlay_path_64() {
        return this.play_path_64;
    }

    public void setPlay_path_64(String play_path_64) {
        this.play_path_64 = play_path_64;
    }

    public long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPlay_path() {
        return this.play_path;
    }

    public void setPlay_path(String play_path) {
        this.play_path = play_path;
    }

    public boolean getIs_public() {
        return this.is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public long getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCover_path() {
        return this.cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public int getUser_source() {
        return this.user_source;
    }

    public void setUser_source(int user_source) {
        this.user_source = user_source;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public long getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public String getPlay_path_32() {
        return this.play_path_32;
    }

    public void setPlay_path_32(String play_path_32) {
        this.play_path_32 = play_path_32;
    }

    public String getDownload_path() {
        return this.download_path;
    }

    public void setDownload_path(String download_path) {
        this.download_path = download_path;
    }

    public int getUpload_source() {
        return this.upload_source;
    }

    public void setUpload_source(int upload_source) {
        this.upload_source = upload_source;
    }

    public String getAlbum_cover_path() {
        return this.album_cover_path;
    }

    public void setAlbum_cover_path(String album_cover_path) {
        this.album_cover_path = album_cover_path;
    }

    public int getAlbum_id() {
        return this.album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_title() {
        return this.album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public boolean getIs_v() {
        return this.is_v;
    }

    public void setIs_v(boolean is_v) {
        this.is_v = is_v;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_path() {
        return this.avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public int getCount_play() {
        return this.count_play;
    }

    public void setCount_play(int count_play) {
        this.count_play = count_play;
    }

    public int getCount_comment() {
        return this.count_comment;
    }

    public void setCount_comment(int count_comment) {
        this.count_comment = count_comment;
    }

    public int getCount_share() {
        return this.count_share;
    }

    public void setCount_share(int count_share) {
        this.count_share = count_share;
    }

    public int getCount_like() {
        return this.count_like;
    }

    public void setCount_like(int count_like) {
        this.count_like = count_like;
    }

    public String getSmallLogo() {
        return this.smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public boolean getIs_like() {
        return this.is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public boolean getIs_playing() {
        return this.is_playing;
    }

    public void setIs_playing(boolean is_playing) {
        this.is_playing = is_playing;
    }

    public boolean getIsRelay() {
        return this.isRelay;
    }

    public void setIsRelay(boolean isRelay) {
        this.isRelay = isRelay;
    }

    public String getPlay_path_aac_v224() {
        return this.play_path_aac_v224;
    }

    public void setPlay_path_aac_v224(String play_path_aac_v224) {
        this.play_path_aac_v224 = play_path_aac_v224;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchSound that = (SearchSound) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
