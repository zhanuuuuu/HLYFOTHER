package com.hlyf.controller;


import com.hlyf.tool.DB;
import com.hlyf.tool.GetConnection;
import com.hlyf.tool.ResultSet_To_JSON;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Administrator on 2018-03-30.
 * @WebServlet(urlPatterns="/myservlet", description="Servlet的说明")
 */
@WebServlet(urlPatterns="/myservlet", description="Servlet的说明")
public class WebController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setContentType("text/html");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String str=req.getParameter("no");

        PrintWriter out = resp.getWriter();

        String cSheetno=req.getParameter("cSheetno");

        PreparedStatement past=null;
        ResultSet rs=null;
        Connection conn=null;
        try{
            if(cSheetno!=null){
                conn= GetConnection.getStoreConn();
                past=conn.prepareStatement("SELECT A.cSheetno,A.cGoodsNo,cGoodsName,cBarcode,cUnit,cSpec,fQuantity,fInPrice,fInMoney,B.cStoreNo,EndQty=(C.EndQty,0) FROM wh_RbdWarehouseDetail  A  "+
                        "	INNER JOIN wh_RbdWarehouse B ON A.cSheetno=B.cSheetno AND A.cSheetno= ?  "+
                        "	LEFT JOIN t_goodsKuCurQty_wei C ON C.cStoreNo=B.cStoreNo AND C.cGoodsNo=A.cGoodsNo ");
                past.setString(1, cSheetno);
                rs=past.executeQuery();
                JSONArray array= ResultSet_To_JSON.resultSetToJsonArray(rs);
                if(array!=null&&array.length()>0){
                    out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString() + "}");
                }
                else{
                    out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString() + "}");
                }
            }else{
                out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":" + null + "}");
            }
        }catch (Exception e) {
            out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":" + null + "}");
            e.printStackTrace();
        }
        finally{
            DB.closeResultSet(rs);
            DB.closePreparedStatement(past);
            DB.closeConn(conn);
            out.flush();
            out.close();
        }
    }
}
