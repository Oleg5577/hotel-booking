package com.pronovich.hotelbooking.entity;

import com.pronovich.hotelbooking.entity.entityproperties.RequestStatus;
import com.pronovich.hotelbooking.entity.entityproperties.RoomType;

import java.io.Serializable;
import java.util.Date;

public class RoomRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    //TODO Date or String
    private Date checkInDate;

    private String checkOutDate;

    private RoomType roomType;

    private Integer roomSize;

    private RequestStatus requestStatus;

    private Integer userId;

    public RoomRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomRequest that = (RoomRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (checkInDate != null ? !checkInDate.equals(that.checkInDate) : that.checkInDate != null) return false;
        if (checkOutDate != null ? !checkOutDate.equals(that.checkOutDate) : that.checkOutDate != null) return false;
        if (roomType != that.roomType) return false;
        if (roomSize != null ? !roomSize.equals(that.roomSize) : that.roomSize != null) return false;
        if (requestStatus != that.requestStatus) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (checkInDate != null ? checkInDate.hashCode() : 0);
        result = 31 * result + (checkOutDate != null ? checkOutDate.hashCode() : 0);
        result = 31 * result + (roomType != null ? roomType.hashCode() : 0);
        result = 31 * result + (roomSize != null ? roomSize.hashCode() : 0);
        result = 31 * result + (requestStatus != null ? requestStatus.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", roomType=" + roomType +
                ", roomSize=" + roomSize +
                ", requestStatus=" + requestStatus;
    }
}
