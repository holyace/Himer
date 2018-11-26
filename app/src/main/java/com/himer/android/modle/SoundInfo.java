/**
 *
 */
package com.himer.android.modle;

/**
 * @author 创建人        Alvin
 * @author 修改人        Alvin
 * @author 修改说明    调整登录
 * @version 1.0
 * @功能 声音的父类（所有列表声音的共同父类）
 * @date 创建日期    2013-5-28
 * @date 修改日期    2013-5-28
 */

public class SoundInfo {
    public long trackId;
    public String title;
    public String playUrl32;
    public String playUrl64;
    public String playUrlAac224;
    public String coverSmall;
    public String coverLarge;
    public long filesize;
    public double duration;
    public long create_at;
    //审核状态，0：审核中， 1：通过，默认为通过
    public int status = 1;
    //历史收听时间
    public double history_listener;
    public double history_duration;
    public long orderNum;

    public long uid;
    public String nickname;
    public String userCoverPath;

    public long albumId;
    public String albumName;
    public String albumCoverPath;

    public int plays_counts;
    public int favorites_counts;
    public boolean is_favorited;
    public boolean isRelay;
    public int comments_counts;
    public int shares_counts;
    public int user_source;

    public boolean is_playing = false;
    public boolean isPublic;

    public SoundInfo() {

    }

//	public SoundInfo(SoundDetails soundDetails)
//	{
//		this.trackId = soundDetails.trackId;
//		this.title = soundDetails.title;
//		this.playUrl32 = soundDetails.playUrl32;
//		this.playUrl64 = soundDetails.playUrl64;
//		this.coverSmall = soundDetails.coverSmall;
//		this.filesize = soundDetails.filesize;
//		this.duration = soundDetails.duration;
//		this.create_at = soundDetails.create_at;
//		this.orderNum = soundDetails.orderNum;
//		this.uid = soundDetails.uid;
//		this.nickname = soundDetails.nickname;
//		this.userCoverPath = soundDetails.userCoverPath;
//		this.albumId = soundDetails.albumId;
//		this.albumName = soundDetails.albumName;
//		this.albumCoverPath = soundDetails.albumCoverPath;
//		this.plays_counts = soundDetails.plays_counts;
//		this.favorites_counts = soundDetails.favorites_counts;
//		this.is_favorited = soundDetails.is_favorited;
//		this.isRelay = soundDetails.isRelay;
//		this.comments_counts = soundDetails.comments_counts;
//		this.shares_counts = soundDetails.shares_counts;
//		this.user_source = soundDetails.user_source;
//		this.is_playing = soundDetails.is_playing;
//	}

    @Override
    public boolean equals(Object o) {
        if (null == o)
            return false;
        else if (o == this)
            return true;
        else if (o instanceof SoundInfo && ((SoundInfo) o).trackId != 0 && ((SoundInfo) o).trackId == this.trackId) {
            return true;
        }
        return false;
    }
}
