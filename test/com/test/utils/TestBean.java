package com.test.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class TestBean {

    private String CBh;
    private String CAjxxBh;
    private String CAjxxAh;
    private String CAjxxMc;
    private String CAjxxYsfy;
    private Timestamp DAjxxLarq;
    private String CAjxxJbfy;
    private String CAjxxSqr;
    private String CAjxxCbr;
    private String CAjxxSjy;
    private String CAjxxAy;
    private String CAjxxZxmy;
    private String CAjxxSqbdw;
    private Double NAjxxSqbde;
    private String CAjxxSqbdxw;
    private String CDsrXh;
    private Integer NDsrLx;
    private String CDsrMc;
    private Integer NDsrZjlx;
    private String CDsrZjhm;
    private String CDsrSfzfzjg;
    private String CDsrZzjgdm;
    private String CDsrGsdjh;
    private String CDsrZcd;
    private Integer NDsrXb;
    private Integer NDsrGj;
    private String CDsrDz;
    private Integer NDsrSsdwdm;
    private String CDsrSsdwmc;
    private Timestamp DDsrCsrq;
    private Integer NDsrMz;
    private Integer NDsrHxzk;
    private Integer NDsrZzmm;

    public static List<TestBean> randomBean(int count) {
        List<TestBean> resultList = new ArrayList<TestBean>();
        for (int i = 0; i < count; i++) {
            resultList.add(randomBean());
        }
        return resultList;
    }

    public static TestBean randomBean() {

        // 2010年1月1日------------2014年9月1日
        double r = Math.random() * 147225600000d + 1262275200000d;
        Timestamp tt = new Timestamp((long) r);

        String fy = "2400";
        String ry = DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase();

        String ah = "（" + new SimpleDateFormat("yyyy").format(tt) + "）辽执字第" + (int) (Math.random() * 10000) + "号";
        int sf = new Random().nextBoolean() ? 1 : 2;

        TestBean zz = new TestBean();
        zz.setCBh(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setCAjxxBh(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setCAjxxAh(ah);
        zz.setCAjxxYsfy(fy);
        zz.setCAjxxMc(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setDAjxxLarq(tt);
        zz.setCAjxxJbfy(fy);
        zz.setCAjxxSqr(ry);
        zz.setCAjxxCbr(ry);
        zz.setCAjxxSjy(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setCAjxxAy(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setCAjxxZxmy("依据" + ah + "判决书");
        zz.setNAjxxSqbde((double) ((int) (Math.random() * 1000000 + 1000)));
        zz.setCAjxxSqbdxw("道歉");

        zz.setCDsrXh(DigestUtils.md5Hex(UUID.randomUUID().toString()).toUpperCase());
        zz.setNDsrLx(sf);
        zz.setNDsrGj(1);
        zz.setCDsrDz("住址");
        zz.setCDsrSsdwmc("被执行人");
        zz.setDDsrCsrq(tt);
        zz.setNDsrMz(1);
        zz.setNDsrHxzk(null);
        zz.setNDsrZzmm(null);

        return zz;
    }

    public String getCBh() {
        return CBh;
    }

    public void setCBh(String cBh) {
        CBh = cBh;
    }

    public String getCAjxxBh() {
        return CAjxxBh;
    }

    public void setCAjxxBh(String cAjxxBh) {
        CAjxxBh = cAjxxBh;
    }

    public String getCAjxxAh() {
        return CAjxxAh;
    }

    public void setCAjxxAh(String cAjxxAh) {
        CAjxxAh = cAjxxAh;
    }

    public String getCAjxxMc() {
        return CAjxxMc;
    }

    public void setCAjxxMc(String cAjxxMc) {
        CAjxxMc = cAjxxMc;
    }

    public String getCAjxxYsfy() {
        return CAjxxYsfy;
    }

    public void setCAjxxYsfy(String cAjxxYsfy) {
        CAjxxYsfy = cAjxxYsfy;
    }

    public Timestamp getDAjxxLarq() {
        return DAjxxLarq;
    }

    public void setDAjxxLarq(Timestamp dAjxxLarq) {
        DAjxxLarq = dAjxxLarq;
    }

    public String getCAjxxJbfy() {
        return CAjxxJbfy;
    }

    public void setCAjxxJbfy(String cAjxxJbfy) {
        CAjxxJbfy = cAjxxJbfy;
    }

    public String getCAjxxSqr() {
        return CAjxxSqr;
    }

    public void setCAjxxSqr(String cAjxxSqr) {
        CAjxxSqr = cAjxxSqr;
    }

    public String getCAjxxCbr() {
        return CAjxxCbr;
    }

    public void setCAjxxCbr(String cAjxxCbr) {
        CAjxxCbr = cAjxxCbr;
    }

    public String getCAjxxSjy() {
        return CAjxxSjy;
    }

    public void setCAjxxSjy(String cAjxxSjy) {
        CAjxxSjy = cAjxxSjy;
    }

    public String getCAjxxAy() {
        return CAjxxAy;
    }

    public void setCAjxxAy(String cAjxxAy) {
        CAjxxAy = cAjxxAy;
    }

    public String getCAjxxZxmy() {
        return CAjxxZxmy;
    }

    public void setCAjxxZxmy(String cAjxxZxmy) {
        CAjxxZxmy = cAjxxZxmy;
    }

    public String getCAjxxSqbdw() {
        return CAjxxSqbdw;
    }

    public void setCAjxxSqbdw(String cAjxxSqbdw) {
        CAjxxSqbdw = cAjxxSqbdw;
    }

    public Double getNAjxxSqbde() {
        return NAjxxSqbde;
    }

    public void setNAjxxSqbde(Double nAjxxSqbde) {
        NAjxxSqbde = nAjxxSqbde;
    }

    public String getCAjxxSqbdxw() {
        return CAjxxSqbdxw;
    }

    public void setCAjxxSqbdxw(String cAjxxSqbdxw) {
        CAjxxSqbdxw = cAjxxSqbdxw;
    }

    public String getCDsrXh() {
        return CDsrXh;
    }

    public void setCDsrXh(String cDsrXh) {
        CDsrXh = cDsrXh;
    }

    public Integer getNDsrLx() {
        return NDsrLx;
    }

    public void setNDsrLx(Integer nDsrLx) {
        NDsrLx = nDsrLx;
    }

    public String getCDsrMc() {
        return CDsrMc;
    }

    public void setCDsrMc(String cDsrMc) {
        CDsrMc = cDsrMc;
    }

    public Integer getNDsrZjlx() {
        return NDsrZjlx;
    }

    public void setNDsrZjlx(Integer nDsrZjlx) {
        NDsrZjlx = nDsrZjlx;
    }

    public String getCDsrZjhm() {
        return CDsrZjhm;
    }

    public void setCDsrZjhm(String cDsrZjhm) {
        CDsrZjhm = cDsrZjhm;
    }

    public String getCDsrSfzfzjg() {
        return CDsrSfzfzjg;
    }

    public void setCDsrSfzfzjg(String cDsrSfzfzjg) {
        CDsrSfzfzjg = cDsrSfzfzjg;
    }

    public String getCDsrZzjgdm() {
        return CDsrZzjgdm;
    }

    public void setCDsrZzjgdm(String cDsrZzjgdm) {
        CDsrZzjgdm = cDsrZzjgdm;
    }

    public String getCDsrGsdjh() {
        return CDsrGsdjh;
    }

    public void setCDsrGsdjh(String cDsrGsdjh) {
        CDsrGsdjh = cDsrGsdjh;
    }

    public String getCDsrZcd() {
        return CDsrZcd;
    }

    public void setCDsrZcd(String cDsrZcd) {
        CDsrZcd = cDsrZcd;
    }

    public Integer getNDsrXb() {
        return NDsrXb;
    }

    public void setNDsrXb(Integer nDsrXb) {
        NDsrXb = nDsrXb;
    }

    public Integer getNDsrGj() {
        return NDsrGj;
    }

    public void setNDsrGj(Integer nDsrGj) {
        NDsrGj = nDsrGj;
    }

    public String getCDsrDz() {
        return CDsrDz;
    }

    public void setCDsrDz(String cDsrDz) {
        CDsrDz = cDsrDz;
    }

    public Integer getNDsrSsdwdm() {
        return NDsrSsdwdm;
    }

    public void setNDsrSsdwdm(Integer nDsrSsdwdm) {
        NDsrSsdwdm = nDsrSsdwdm;
    }

    public String getCDsrSsdwmc() {
        return CDsrSsdwmc;
    }

    public void setCDsrSsdwmc(String cDsrSsdwmc) {
        CDsrSsdwmc = cDsrSsdwmc;
    }

    public Timestamp getDDsrCsrq() {
        return DDsrCsrq;
    }

    public void setDDsrCsrq(Timestamp dDsrCsrq) {
        DDsrCsrq = dDsrCsrq;
    }

    public Integer getNDsrMz() {
        return NDsrMz;
    }

    public void setNDsrMz(Integer nDsrMz) {
        NDsrMz = nDsrMz;
    }

    public Integer getNDsrHxzk() {
        return NDsrHxzk;
    }

    public void setNDsrHxzk(Integer nDsrHxzk) {
        NDsrHxzk = nDsrHxzk;
    }

    public Integer getNDsrZzmm() {
        return NDsrZzmm;
    }

    public void setNDsrZzmm(Integer nDsrZzmm) {
        NDsrZzmm = nDsrZzmm;
    }

}
