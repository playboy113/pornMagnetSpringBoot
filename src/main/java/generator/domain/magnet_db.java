package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName magnet_db
 */
@TableName(value ="magnet_db")
public class magnet_db implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String types;

    /**
     * 
     */
    private String actress;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String magnet;

    /**
     * 
     */
    private String subline;

    /**
     * 
     */
    private String num;

    /**
     * 
     */
    private String date;

    /**
     * 
     */
    private String hd;

    /**
     * 
     */
    private String producer;

    /**
     * 
     */
    private String series;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getTypes() {
        return types;
    }

    /**
     * 
     */
    public void setTypes(String types) {
        this.types = types;
    }

    /**
     * 
     */
    public String getActress() {
        return actress;
    }

    /**
     * 
     */
    public void setActress(String actress) {
        this.actress = actress;
    }

    /**
     * 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     */
    public String getMagnet() {
        return magnet;
    }

    /**
     * 
     */
    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }

    /**
     * 
     */
    public String getSubline() {
        return subline;
    }

    /**
     * 
     */
    public void setSubline(String subline) {
        this.subline = subline;
    }

    /**
     * 
     */
    public String getNum() {
        return num;
    }

    /**
     * 
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     */
    public String getHd() {
        return hd;
    }

    /**
     * 
     */
    public void setHd(String hd) {
        this.hd = hd;
    }

    /**
     * 
     */
    public String getProducer() {
        return producer;
    }

    /**
     * 
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * 
     */
    public String getSeries() {
        return series;
    }

    /**
     * 
     */
    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        magnet_db other = (magnet_db) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTypes() == null ? other.getTypes() == null : this.getTypes().equals(other.getTypes()))
            && (this.getActress() == null ? other.getActress() == null : this.getActress().equals(other.getActress()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getMagnet() == null ? other.getMagnet() == null : this.getMagnet().equals(other.getMagnet()))
            && (this.getSubline() == null ? other.getSubline() == null : this.getSubline().equals(other.getSubline()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getHd() == null ? other.getHd() == null : this.getHd().equals(other.getHd()))
            && (this.getProducer() == null ? other.getProducer() == null : this.getProducer().equals(other.getProducer()))
            && (this.getSeries() == null ? other.getSeries() == null : this.getSeries().equals(other.getSeries()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypes() == null) ? 0 : getTypes().hashCode());
        result = prime * result + ((getActress() == null) ? 0 : getActress().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getMagnet() == null) ? 0 : getMagnet().hashCode());
        result = prime * result + ((getSubline() == null) ? 0 : getSubline().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getHd() == null) ? 0 : getHd().hashCode());
        result = prime * result + ((getProducer() == null) ? 0 : getProducer().hashCode());
        result = prime * result + ((getSeries() == null) ? 0 : getSeries().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", types=").append(types);
        sb.append(", actress=").append(actress);
        sb.append(", title=").append(title);
        sb.append(", magnet=").append(magnet);
        sb.append(", subline=").append(subline);
        sb.append(", num=").append(num);
        sb.append(", date=").append(date);
        sb.append(", hd=").append(hd);
        sb.append(", producer=").append(producer);
        sb.append(", series=").append(series);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}