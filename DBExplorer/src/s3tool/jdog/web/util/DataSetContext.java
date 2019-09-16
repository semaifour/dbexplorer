package s3tool.jdog.web.util;


import javax.servlet.http.HttpServletRequest;

import com.silrais.toolkit.util.SimpleUtil;
import com.silrais.webtoolkit.util.HttpUtil;

public class DataSetContext implements Cloneable {

    protected String dsid; //data set id 
//    protected String dsaxn; //data set action (nxt, prv, srt)
    protected String scol; //sort column
    protected String scolidx; //sort column
    protected int cfrid; //current first row id
    protected String dmode;
    protected String sodr; //sort order

    protected String httpParamStr = "&e=e";


    public DataSetContext() { }

    public String getdsid() {
        return this.dsid;
    }
    
//    public String getdsaxn() {
//        return this.dsaxn;
//    }
    
    public String getscol() {
        return this.scol;
    }
    
    public int getcfrid() {
        return this.cfrid;
    }

    public void setscol(String scol) {
        this.scol = scol;
    }

	public String getscolidx() {
		return scolidx;
	}

	public void setscolidx(String scolidx) {
		this.scolidx = scolidx;
	}
	
    public void setcfrid(int cfrid) {
        this.cfrid = cfrid;
    }

    public void setdsid(String dsid) {
        this.dsid = dsid;
    }

    public void setdmode(String dmode) {
        this.dmode = dmode;
    }

    public String getdmode() {
        return this.dmode;
    }

    public void setsodr(String sodr) {
        this.sodr = sodr;
    }

    public String getsodr() {
        return sodr;
    }

    public void setContext(HttpServletRequest req) {
        this.dsid   = HttpUtil.getStrAttrB4Param(req, "dsid");
//        this.dsaxn   = HttpUtil.getStrParam(req, "dsaxn");
        this.scol   = HttpUtil.getStrParamB4Attr(req, "scol");
        this.scolidx   = HttpUtil.getStrParamB4Attr(req, "scolidx");
        this.cfrid  = HttpUtil.getIntAttrB4Param(req, "cfrid");
        this.dmode  = HttpUtil.getStrParamB4Attr(req, "dmode");
        this.sodr   = HttpUtil.getStrParamB4Attr(req, "sodr");
    }

    public void buildHttpParamString() {
        StringBuffer params = new StringBuffer();

        //DONT REMOVE
        //http param string must start without '&'
        params.append("dsctx=y");

        if (!SimpleUtil.isnull(dsid)) {
            params.append("&dsid=").append(dsid);
        }
        
//        if (!SimpleUtil.isnull(dsaxn)) {
//            params.append("&dsaxn=").append(dsaxn);
//        }

        if (!SimpleUtil.isnull(scol)) {
            params.append("&scol=").append(scol);
        }

        if (!SimpleUtil.isnull(scol)) {
            params.append("&scolidx=").append(scolidx);
        }

        params.append("&cfrid=").append(cfrid);

        if (!SimpleUtil.isnull(dmode)) {
            params.append("&dmode=").append(dmode);
        }
        if (!SimpleUtil.isnull(dmode)) {
            params.append("&sodr=").append(sodr);
        }



        httpParamStr = params.toString();
    }

    public String getHttpParamString(boolean rebuild) {
        if (rebuild)
            buildHttpParamString();
        return this.httpParamStr;
    }


}
