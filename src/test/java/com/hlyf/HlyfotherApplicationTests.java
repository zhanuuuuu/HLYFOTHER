package com.hlyf;

import com.google.gson.Gson;
import com.hlyf.dao.cluster.mingSheng;
import com.hlyf.dao.cluster.sCanDao;
import com.hlyf.domin.*;
import com.hlyf.domin.JDcom.*;
import com.hlyf.domin.mingsheng.t_MinShengPublicSale_z;
import com.hlyf.domin.mingsheng.t_MingShengPublicSaleDetail_z;
import com.hlyf.domin.mingsheng.t_MingShengTime;
import com.hlyf.minsheng.RopUtils;
import com.hlyf.service.sCanService;
import com.hlyf.service.testService;
import com.hlyf.service.userService;
import com.hlyf.tool.String_Tool;
import com.hlyf.tool.ThreeDESUtil;
import com.hlyf.tool.listBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.hlyf.tool.HttpTookitt.sendPost;
import static com.hlyf.tool.Md5.MD5Encode;
import static com.hlyf.tool.String_Tool.DataBaseTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HlyfotherApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(HlyfotherApplicationTests.class);

	private static final String appkey="warelucent";

	@Autowired
	private testService testService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private sCanService CanService;

	@Autowired
	private userService UserService;

	@Autowired
	private sCanDao CanDao;

	@Autowired
	private mingSheng mingSheng;
	//保存单号
	@Test
	public void testSaveSheetNo(){
		Integer integer=CanDao.p_saveSheetNo_Z_call("1005",
				"05","2019-05-06","05201904060001",
				"32","posstation101.dbo.p_saveSheetNo");

		logger.info("我是影响行数 "+ integer);
	}
	//获取单号
	@Test
	public void testGetCommSheetNo(){
		commSheetNo commSheetNo=CanDao
				.getCommSheetNo("1005","05",
						"2019-05-06","posstation101.dbo.p_getPos_SerialNoSheetNo");
		logger.info("commSheetNo  "+commSheetNo.toString());
	}
	@Test
	public  void testGetpreferentialGoods(){

		List<preferentialGoods> list =CanService.get_preferentialGoodsS("005","81","7",
				"","100","0","posstation005.dbo.p_ProcessPosSheet");

		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}





	}
	@Test
	public void select_posstation(){

		new  Thread(new Runnable() {
			@Override
			public void run() {
				posstation posstation=CanService.select_posstationS("20190417111411","SEWUFFNAJDK","0002");
				if(posstation!=null){

					logger.info("posstation  "+ posstation.toString());
				}else {
					logger.info("posstation  空的啊");
				}

			}
		}).start();



	}
	@Test
	public  void testUpdateVip(){
		Integer list=CanService.update_VipS("zhang","","0000052222",30.0);
		logger.info("我是印象行数"+ list);

	}

	@Test
	public  void testGetShengXianMA(){

		//List<posConfig> list=CanService.selectAllS("posstation101.dbo.Pos_Config","条码秤");
		List<t_vipInfo> list =CanService.get_vipInfoS("000005");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}

	@Test
	public  void testGetUpdateOrder(){

		Map map = new HashMap();
		map.put("cSheetNo", "201904241658002964601");
		map.put("appId","abc");
		map.put("iFlag", 0);

		CanDao.updateOrderTypeToJd(map);
		Integer ids = (Integer)map.get("iFlag");
		logger.info("日志测试"+ids);


	}


	@Test
	public void Testpos_jiesuan(){
		String jsonStr="{\n" +
				"\t\"data\": [{\n" +
				"\t\t\"jiaozhang\": \"0\",\n" +
				"\t\t\"zhekou\": \"100.0000\",\n" +
				"\t\t\"orientmoney\": \"0.0000\",\n" +
				"\t\t\"sheetno\": \"03201812050001\",\n" +
				"\t\t\"iLineNo_Banci\": \"1\",\n" +
				"\t\t\"bPost\": \"0\",\n" +
				"\t\t\"mianzhi\": \"12.2000\",\n" +
				"\t\t\"shishou\": \"12.2000\",\n" +
				"\t\t\"cBanci\": \"早班\",\n" +
				"\t\t\"leftmoney\": \"0.0000\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"jstype\": \"1人民币\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-05_1\",\n" +
				"\t\t\"shouyinyuanno\": \"888\",\n" +
				"\t\t\"shouyinyuanmc\": \"系统维护\",\n" +
				"\t\t\"netjiecun\": \"4\",\n" +
				"\t\t\"jstime\": \"2018-12-05 11:21:57.0\",\n" +
				"\t\t\"detail\": \"人民币\",\n" +
				"\t\t\"zdriqi\": \"2018-12-05 00:00:00.0\",\n" +
				"\t\t\"zhaoling\": \"0.0000\",\n" +
				"\t\t\"storevalue\": \"0\"\n" +
				"\t}]\n" +
				"}";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("data"));
		List<pos_jiesuan> list=JSONArray.toList(jsonArray1,new pos_jiesuan(),new JsonConfig());
		for(pos_jiesuan t:list){
			t.setbPost("0");
			t.setSheetno("123564");
			System.out.println(t.getSheetno());
		}
		Map mapDetail=new HashMap();
		mapDetail.put("list",list);
		mapDetail.put("tableName","posstation101.dbo.pos_jiesuan");
		Integer i=CanDao.insert_pos_jiesuan(mapDetail);

		logger.info("我是影响函数 {}",i);

	}
	//123456789
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			timeout=36000,
			rollbackFor={RuntimeException.class, Exception.class},
			readOnly = false)
	@Test
	public void Test_test_huichuan_POS_SaleSheetDetail(){
		String jsonStr="{\n" +
				"\t\"data\": [{\n" +
				"\t\t\"fDiscount\": \"100.0000\",\n" +
				"\t\t\"fVipPrice\": \"5.5000\",\n" +
				"\t\t\"cSpec\": \"120g\",\n" +
				"\t\t\"fPrice\": \"10.9000\",\n" +
				"\t\t\"fNormalSettle\": \"10.9000\",\n" +
				"\t\t\"cGoodsName\": \"彩虹糖酸劲味120g\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cGoodsNo\": \"1123050001\",\n" +
				"\t\t\"bAuditing\": \"1\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"cBarCode\": \"6923450661529\",\n" +
				"\t\t\"fLastSettle0\": \"5.5000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"fLastSettle\": \"5.5000\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bWeight\": null\n" +
				"\t}, {\n" +
				"\t\t\"fDiscount\": \"100.0000\",\n" +
				"\t\t\"fVipPrice\": \"5.5000\",\n" +
				"\t\t\"cSpec\": \"120g\",\n" +
				"\t\t\"fPrice\": \"10.9000\",\n" +
				"\t\t\"fNormalSettle\": \"10.9000\",\n" +
				"\t\t\"cGoodsName\": \"印字中袋\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cGoodsNo\": \"05000002\",\n" +
				"\t\t\"bAuditing\": \"1\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"cBarCode\": \"6958279812036\",\n" +
				"\t\t\"fLastSettle0\": \"5.5000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"fLastSettle\": \"5.5000\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bWeight\": null\n" +
				"\t}]\n" +
				"}";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("data"));
		List<POS_SaleSheetDetail> list=JSONArray.toList(jsonArray1,new POS_SaleSheetDetail(),new JsonConfig());

		//获取单号
		commSheetNo commSheetNo=null;
		try{
			commSheetNo=CanDao
					.getCommSheetNo("1005","05",
							new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
							"posstation101.dbo.p_getPos_SerialNoSheetNo");
		}catch (Exception e){
			logger.info("生成单号出错(错误信息): {}",e.getMessage());
		}

		int i=1;
		Double sumMoney=0.0;
		String cWHno="";
		for(POS_SaleSheetDetail t:list){
			cWHno=t.getcWHno();

			t.setcSaleTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			t.setbHideQty("0");
			t.setiLineNo_Banci("1");
			t.setbSettle("0");
			t.setcSaleSheetno(commSheetNo.getcSheetNo());//设置单号
			t.setfVipScore("0");
			t.setbChecked("0");
			t.setfVipScore_cur("0");
			//t.setbAuditing("0");
			t.setbVipRate("0");
			t.setiSeed(""+i);
			i++;
			if(t.getbWeight()==null || (!t.getbWeight().trim().equals("0") && !t.getbWeight().trim().equals("1"))){
				t.setbWeight("0");
			}
			//t.setfVipRate("100");
			t.setbPost("0");
			t.setcOperatorno("机器编号");
			t.setcOperatorName("机器名称");
			t.setdSaleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setdFinanceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setfNormalVipScore("0");
			t.setcVipCardno("");
			t.setbVipPrice("0");
			t.setbHidePrice("0");
			t.setcBanci("早班");
			t.setcWorkerno("1");
			t.setcBanci_ID(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1");
			//t.setfAgio("0");
			t.setcVipNo("");

			sumMoney = sumMoney +Double.parseDouble(t.getfLastSettle());
			logger.info("我是单号 {}",t.getcSaleSheetno());
		}

		String str= listBean.getBeanJson(list);
		logger.info("数据集合 POS_SaleSheetDetail"+str);

		List<pos_jiesuan> listpos_jiesuan=new ArrayList<pos_jiesuan>();

		pos_jiesuan posJiesuan=new pos_jiesuan(
				commSheetNo.getcSheetNo(),  //设置单号 commSheetNo.getcSheetNo()
				"1人民币",
				 String.valueOf(sumMoney),
				"0",
				"100",
				String.valueOf(sumMoney),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
				"0",
				"京东编号",
				"京东机器编号",
				""+i,
				"0",
				"0",
				"0",
				"人民币",
				"0",
				cWHno,
				new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1",
				"1",
				"早班");

		listpos_jiesuan.add(posJiesuan);

		str= listBean.getBeanJson(listpos_jiesuan);
		logger.info("数据集合 pos_jiesuan"+str);

		Map mapposDetail=new HashMap();
		mapposDetail.put("list",listpos_jiesuan);
		mapposDetail.put("tableName","posstation101.dbo.pos_jiesuan");


		Map mapDetail=new HashMap();
		mapDetail.put("list",list);
		mapDetail.put("tableName","posstation101.dbo.POS_SaleSheetDetail");

		//下面是自己写的事物判断  因为表名字是动态的  影响了mybatis的事务管理
		// 在这里开启mybatis的事物已经不起作用了  所以自己写事务管理
		try{

			//插入主表
			int a=CanDao.insert_pos_jiesuan(mapposDetail);

			//删除主表
			Map del_pos_jiesuan=new HashMap();
			del_pos_jiesuan.put("sheetno",commSheetNo.getcSheetNo());
			del_pos_jiesuan.put("tableName","posstation101.dbo.pos_jiesuan");

			//删除附表
			Map del_POS_SaleSheetDetail=new HashMap();
			del_POS_SaleSheetDetail.put("sheetno",commSheetNo.getcSheetNo());
			del_POS_SaleSheetDetail.put("tableName","posstation101.dbo.POS_SaleSheetDetail");



			//更改保存单号
			if(a>0){
				int c=0;
				try{
					c=CanDao.insert_POS_SaleSheetDetail(mapDetail);
				}catch (Exception e){
					//删除主表数据
					CanDao.delete_pos_jiesuan(del_pos_jiesuan);
					logger.info("数据保存失败 {}",e.getMessage());
					throw new RuntimeException("京东 订单回传 数据保存失败");
				}
				if(c>0){
					try{
						CanDao.p_saveSheetNo_Z_call("1005",
								"05",
								new SimpleDateFormat("yyyy-MM-dd").format(new Date()),  //
								commSheetNo.getcSheetNo(),//单号
								""+i,
								"posstation101.dbo.p_saveSheetNo");
					}catch (Exception e){

						//删除主表数据
						CanDao.delete_pos_jiesuan(del_pos_jiesuan);
						//删除附表数据
						CanDao.delete_POS_SaleSheetDetail(del_POS_SaleSheetDetail);
						logger.info("数据保存失败 {}",e.getMessage());
						throw new RuntimeException("京东 订单回传 数据保存失败");
					}

				}

			}

		}catch (Exception e){
			logger.info("数据保存失败 {}",e.getMessage());
			throw new RuntimeException("京东 订单回传 数据保存失败");
		}


	}

	@Test
	public void testDel(){
		//删除主表
		Map del_pos_jiesuan=new HashMap();
		del_pos_jiesuan.put("sheetno","03201812050001");
		del_pos_jiesuan.put("tableName","posstation101.dbo.pos_jiesuan");

		//删除附表
		Map del_POS_SaleSheetDetail=new HashMap();
		del_POS_SaleSheetDetail.put("sheetno","03201812050001");
		del_POS_SaleSheetDetail.put("tableName","posstation101.dbo.POS_SaleSheetDetail");

		//删除主表数据
		CanDao.delete_pos_jiesuan(del_pos_jiesuan);
		//删除附表数据
		CanDao.delete_POS_SaleSheetDetail(del_POS_SaleSheetDetail);
	}
	@Test
	public void Test_test_POS_SaleSheetDetail(){
		String jsonStr="{\n" +
				"\t\"data\": [{\n" +
				"\t\t\"fQuantity\": \"1\",\n" +
				"\t\t\"fLastSettle0\": \"0.3000\",\n" +
				"\t\t\"cBarCode\": \"6958279812036\",\n" +
				"\t\t\"fVipPrice\": \"0.3000\",\n" +
				"\t\t\"fPrice\": \"0.3000\",\n" +
				"\t\t\"fNormalSettle\": \"0.3000\",\n" +
				"\t\t\"cGoodsName\": \"印字中袋\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"cGoodsNo\": \"05000002\",\n" +
				"\t\t\"fLastSettle\": \"0.3000\"\n" +
				"\t}, {\n" +
				"\t\t\"fQuantity\": \"5\",\n" +
				"\t\t\"fLastSettle0\": \"10\",\n" +
				"\t\t\"cBarCode\": \"6958031500034\",\n" +
				"\t\t\"fVipPrice\": \"2\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"3\",\n" +
				"\t\t\"fNormalSettle\": \"15\",\n" +
				"\t\t\"cGoodsName\": \"香其酱Z100g\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"cGoodsNo\": \"03001231\",\n" +
				"\t\t\"fLastSettle\": \"10\"\n" +
				"\t}]\n" +
				"}";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("data"));
		List<POS_SaleSheetDetail> list=JSONArray.toList(jsonArray1,new POS_SaleSheetDetail(),new JsonConfig());
		int i=1;
		for(POS_SaleSheetDetail t:list){
			t.setcSaleTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			t.setbHideQty("0");
			t.setiLineNo_Banci("1");
			t.setbSettle("0");
			t.setcSaleSheetno("456");
			t.setfVipScore("0");
			t.setbChecked("0");
			t.setfVipScore_cur("0");
			t.setbAuditing("0");
			t.setbVipRate("0");
			t.setiSeed(""+i);
			i++;
			if(t.getbWeight()==null || (!t.getbWeight().trim().equals("0") && !t.getbWeight().trim().equals("1"))){
				t.setbWeight("0");
			}
			t.setfVipRate("100");
			t.setbPost("0");
			t.setcOperatorno("机器编号");
			t.setcOperatorName("机器名称");
			t.setdSaleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setdFinanceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setfNormalVipScore("0");
			t.setcVipCardno("");
			t.setbVipPrice("0");
			t.setbHidePrice("0");
			t.setcBanci("早班");
			t.setcWorkerno("1");
			t.setcBanci_ID(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1");
			t.setfAgio("0");
			t.setcVipNo("");
			logger.info("我是单号 {}",t.getcSaleSheetno());
		}

		String str= listBean.getBeanJson(list);
		logger.info("数据集合 POS_SaleSheetDetail"+str);

		Map mapDetail=new HashMap();
		mapDetail.put("list",list);
		mapDetail.put("tableName","posstation101.dbo.POS_SaleSheetDetail");
		Integer n=CanDao.insert_POS_SaleSheetDetail(mapDetail);

		logger.info("我是影响函数 {}",n);

	}

	@Test
	public void TestPOS_SaleSheetDetail(){
		String jsonStr="{\n" +
				"\t\"data\": [{\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"1\",\n" +
				"\t\t\"fQuantity\": \"3.2900\",\n" +
				"\t\t\"fLastSettle0\": \"3.1600\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"02641\",\n" +
				"\t\t\"fVipPrice\": \"0.9600\",\n" +
				"\t\t\"bWeight\": \"1\",\n" +
				"\t\t\"fPrice\": \"0.9600\",\n" +
				"\t\t\"fNormalSettle\": \"3.1600\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"京白菜\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"-0.0100\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"01000076\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"3.1700\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"2\",\n" +
				"\t\t\"fQuantity\": \"2.7280\",\n" +
				"\t\t\"fLastSettle0\": \"2.6200\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"02641\",\n" +
				"\t\t\"fVipPrice\": \"0.9600\",\n" +
				"\t\t\"bWeight\": \"1\",\n" +
				"\t\t\"fPrice\": \"0.9600\",\n" +
				"\t\t\"fNormalSettle\": \"2.6200\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"京白菜\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"01000076\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"2.6200\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.0300\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"3\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"0.3000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6958279812036\",\n" +
				"\t\t\"fVipPrice\": \"0.3000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"0.3000\",\n" +
				"\t\t\"fNormalSettle\": \"0.3000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"印字中袋\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"05000002\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.0300\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"0.3000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.0300\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"4\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"0.3000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6958279812036\",\n" +
				"\t\t\"fVipPrice\": \"0.3000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"0.3000\",\n" +
				"\t\t\"fNormalSettle\": \"0.3000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"印字中袋\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"05000002\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.0300\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"0.3000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"5\",\n" +
				"\t\t\"fQuantity\": \"2.7700\",\n" +
				"\t\t\"fLastSettle0\": \"2.6600\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"02641\",\n" +
				"\t\t\"fVipPrice\": \"0.9600\",\n" +
				"\t\t\"bWeight\": \"1\",\n" +
				"\t\t\"fPrice\": \"0.9600\",\n" +
				"\t\t\"fNormalSettle\": \"2.6600\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"京白菜\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"-0.0100\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"01000076\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"2.6700\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"1.2900\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"6\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"12.9000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6970216931174\",\n" +
				"\t\t\"fVipPrice\": \"12.9000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"12.9000\",\n" +
				"\t\t\"fNormalSettle\": \"12.9000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"筷之语KZY587工艺竹筷（神采飞扬）5双混装KG\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"04000720\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"1.2900\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"12.9000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"7\",\n" +
				"\t\t\"fQuantity\": \"2.8440\",\n" +
				"\t\t\"fLastSettle0\": \"2.7300\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"02641\",\n" +
				"\t\t\"fVipPrice\": \"0.9600\",\n" +
				"\t\t\"bWeight\": \"1\",\n" +
				"\t\t\"fPrice\": \"0.9600\",\n" +
				"\t\t\"fNormalSettle\": \"2.7300\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"京白菜\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"-0.0100\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"01000076\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"2.7400\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.9900\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"8\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"9.9000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6920174742954\",\n" +
				"\t\t\"fVipPrice\": \"9.9000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"9.9000\",\n" +
				"\t\t\"fNormalSettle\": \"9.9000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"立白薰衣草柔顺剂\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"04002320\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.9900\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"9.9000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1500\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"9\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"1.5000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6958031500034\",\n" +
				"\t\t\"fVipPrice\": \"1.5000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"1.5000\",\n" +
				"\t\t\"fNormalSettle\": \"1.5000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"香其酱Z100g\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"03001231\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1500\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"1.5000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1400\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"10\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"1.4000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6934423500127\",\n" +
				"\t\t\"fVipPrice\": \"1.4000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"1.4000\",\n" +
				"\t\t\"fNormalSettle\": \"1.4000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"厨众味源营口酱170g\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"03001230\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1400\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"1.4000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1200\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"11\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"1.2000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6941870950337\",\n" +
				"\t\t\"fVipPrice\": \"1.2000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"1.2000\",\n" +
				"\t\t\"fNormalSettle\": \"1.2000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"王致和天骄陈香沙棘醋300ml\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"03001056\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1200\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"1.2000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.1200\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"12\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"1.2000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6941870950337\",\n" +
				"\t\t\"fVipPrice\": \"1.2000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"1.2000\",\n" +
				"\t\t\"fNormalSettle\": \"1.2000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"王致和天骄陈香沙棘醋300ml\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"03001056\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.1200\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"1.2000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.4800\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"13\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"4.8000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6917790976269\",\n" +
				"\t\t\"fVipPrice\": \"4.8000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"4.8000\",\n" +
				"\t\t\"fNormalSettle\": \"4.8000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"安琪高活性干酵母Z100g\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"03000676\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.4800\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"4.8000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.3000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"14\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"3.0000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6939628012536\",\n" +
				"\t\t\"fVipPrice\": \"3.0000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"3.0000\",\n" +
				"\t\t\"fNormalSettle\": \"3.0000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"宏隆1253小号仿毛线镂空纸篓\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"04000387\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.3000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"3.0000\"\n" +
				"\t}, {\n" +
				"\t\t\"cSaleTime\": \"17:15:21\",\n" +
				"\t\t\"bHideQty\": \"0\",\n" +
				"\t\t\"cVipNo\": \"\",\n" +
				"\t\t\"iLineNo_Banci\": \"2\",\n" +
				"\t\t\"bSettle\": \"0\",\n" +
				"\t\t\"cSaleSheetno\": \"03201812060082\",\n" +
				"\t\t\"fVipScore\": \"0.6000\",\n" +
				"\t\t\"bChecked\": \"0\",\n" +
				"\t\t\"fVipScore_cur\": \"0.0000\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"bVipRate\": \"0\",\n" +
				"\t\t\"iSeed\": \"15\",\n" +
				"\t\t\"fQuantity\": \"1.0000\",\n" +
				"\t\t\"fLastSettle0\": \"6.0000\",\n" +
				"\t\t\"fVipRate\": \"100.0000\",\n" +
				"\t\t\"cBarCode\": \"6939628012413\",\n" +
				"\t\t\"fVipPrice\": \"6.0000\",\n" +
				"\t\t\"bWeight\": null,\n" +
				"\t\t\"fPrice\": \"6.0000\",\n" +
				"\t\t\"fNormalSettle\": \"6.0000\",\n" +
				"\t\t\"bPost\": \"1\",\n" +
				"\t\t\"cOperatorName\": \"任换莲\",\n" +
				"\t\t\"cGoodsName\": \"宏隆1241仿毛线镂空收纳盒\",\n" +
				"\t\t\"cOperatorno\": \"10004\",\n" +
				"\t\t\"dSaleDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"cBanci\": \"中班\",\n" +
				"\t\t\"fAgio\": \"0.0000\",\n" +
				"\t\t\"cWorkerno\": \"1\",\n" +
				"\t\t\"cWHno\": \"1010\",\n" +
				"\t\t\"bVipPrice\": \"0\",\n" +
				"\t\t\"cGoodsNo\": \"04000384\",\n" +
				"\t\t\"cVipCardno\": null,\n" +
				"\t\t\"fNormalVipScore\": \"0.6000\",\n" +
				"\t\t\"cBanci_ID\": \"2018-12-06_2\",\n" +
				"\t\t\"dFinanceDate\": \"2018-12-06 00:00:00.0\",\n" +
				"\t\t\"bHidePrice\": \"0\",\n" +
				"\t\t\"fLastSettle\": \"6.0000\"\n" +
				"\t}]\n" +
				"}";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("data"));
		List<POS_SaleSheetDetail> list=JSONArray.toList(jsonArray1,new POS_SaleSheetDetail(),new JsonConfig());
		int i=1;
		for(POS_SaleSheetDetail t:list){
			t.setcSaleTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			t.setbHideQty("0");
			t.setiLineNo_Banci("1");
			t.setbSettle("0");
			t.setfVipScore("0");
			t.setbChecked("0");
			t.setfVipScore_cur("0");
			t.setbAuditing("0");
			t.setbVipRate("0");
			t.setiSeed(""+i);
			i++;
			t.setfVipRate("100");
			t.setbPost("0");
			t.setcOperatorno("机器编号");
			t.setcOperatorName("机器名称");
			t.setdSaleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setdFinanceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			t.setfNormalVipScore("0");
			t.setcVipCardno("");
			t.setbVipPrice("0");
			t.setbHidePrice("0");
			t.setcBanci("早班");
			t.setcWorkerno("1");
			t.setcBanci_ID(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_1");
			t.setfAgio("0");

			t.setcSaleSheetno("123");
			logger.info("我是单号 {}",t.getcSaleSheetno());
		}
		Map mapDetail=new HashMap();
		mapDetail.put("list",list);
		mapDetail.put("tableName","posstation101.dbo.POS_SaleSheetDetail");
		Integer n=CanDao.insert_POS_SaleSheetDetail(mapDetail);

		logger.info("我是影响函数 {}",n);

	}

    @Test
	public void testgetPOS_SaleSheetDetail(){
		List<POS_SaleSheetDetail> list=CanDao.get_POS_SaleSheetDetail();
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}
	@Test
	public void TestLambda(){
		List<pos_jiesuan> list=CanDao.get_pos_jiesuan();
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}
	//测试京东下单
	@Test
	public void testJDXiadanTrue(){


		String jsonStr="{\n" +
				"\t\"sale\": [{\n" +
				"\t\t\"actualMoney\": \"25.30\",\n" +
				"\t\t\"saleTime\": \"2018-11-24 16:44:30\",\n" +
				"\t\t\"cStoreNo\": \"0002\",\n" +
				"\t\t\"saleTypeName\": \"支付宝支付\",\n" +
				"\t\t\"saleType\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"discountMoney\": \"4.70\",\n" +
				"\t\t\"saleAllMoney\": \"30\",\n" +
				"\t\t\"cSheetNo\": \"15430490706490\",\n" +
				"\t\t\"appSheetNo\": \"15430490706490\",\n" +
				"\t\t\"cVipNo\": \"13628672210\",\n" +
				"\t\t\"machineIp\": \"02\",\n" +
				"\t\t\"cWHno\": \"000\"\n" +
				"\t}],\n" +
				"\t\"saleDatial\": [{\n" +
				"\t\t\"cStoreNo\": \"0002\",\n" +
				"\t\t\"bWeight\": \"0\",\n" +
				"\t\t\"discountPrice\": \"2.70\",\n" +
				"\t\t\"cGoodsName\": \"彩虹果汁糖(酸劲味)\",\n" +
				"\t\t\"discountMoney\": \"2.70\",\n" +
				"\t\t\"cSheetNo\": \"000220191450\",\n" +
				"\t\t\"cWHno\": \"0002\",\n" +
				"\t\t\"cGoodsNo\": \"1010020028\",\n" +
				"\t\t\"fNormalPrice\": \"2.70\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"1\",\n" +
				"\t\t\"fQuantity\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"machineIp\": \"02\",\n" +
				"\t\t\"cBarcode\": \"6928804014648\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"0002\",\n" +
				"\t\t\"discountPrice\": \"1.8\",\n" +
				"\t\t\"cGoodsName\": \"彩虹果汁糖\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"000220191450\",\n" +
				"\t\t\"cWHno\": \"0002\",\n" +
				"\t\t\"cGoodsNo\": \"1122000005\",\n" +
				"\t\t\"fNormalPrice\": \"3.5\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"2\",\n" +
				"\t\t\"fQuantity\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"machineIp\": \"02\",\n" +
				"\t\t\"bWeight\": \"1\",\n" +
				"\t\t\"cBarcode\": \"6923450603550\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"0002\",\n" +
				"\t\t\"discountPrice\": \"1.8\",\n" +
				"\t\t\"cGoodsName\": \"彩虹糖\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"000220191450\",\n" +
				"\t\t\"cWHno\": \"0002\",\n" +
				"\t\t\"cGoodsNo\": \"1122000007\",\n" +
				"\t\t\"fNormalPrice\": \"10.9\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"3\",\n" +
				"\t\t\"fQuantity\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"machineIp\": \"02\",\n" +
				"\t\t\"bWeight\": \"025\",\n" +
				"\t\t\"cBarcode\": \"6923450603666\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"0002\",\n" +
				"\t\t\"discountPrice\": \"5.3\",\n" +
				"\t\t\"cGoodsName\": \"益达木糖醇香橙薄荷味\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"000220191450\",\n" +
				"\t\t\"cWHno\": \"0002\",\n" +
				"\t\t\"cGoodsNo\": \"1123000009\",\n" +
				"\t\t\"fNormalPrice\": \"10.5\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"9\",\n" +
				"\t\t\"fQuantity\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"machineIp\": \"02\",\n" +
				"\t\t\"cBarcode\": \"6923450656860\"\n" +
				"\t}]\n" +
				"}";
		String danhao="24";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("sale"));
		JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("saleDatial"));
		List<tPublicSaleDetail_JingDong_z> list=JSONArray.toList(jsonArray2,new tPublicSaleDetail_JingDong_z(),new JsonConfig());
		for(tPublicSaleDetail_JingDong_z t:list){
			t.setcSheetNo(danhao);
			t.setcVipNo("13628672210");
		}
		String str= listBean.getBeanJson(list);
		logger.info("数据集合 tPublicSaleDetail_JingDong_z"+str);
		List<tPublicSale_JingDong_z> list1=JSONArray.toList(jsonArray1,new tPublicSale_JingDong_z(),new JsonConfig());

		for(tPublicSale_JingDong_z t:list1){
			t.setcSheetNo(danhao);
			t.setcVipNo("13628672210");
			t.setAppSheetNo(danhao);
		}

		str= listBean.getBeanJson(list1);
		logger.info("数据集合 tPublicSale_JingDong_z"+str);

		//String result=CanService.placing_order_JD(list1,list);

		posstation posstation=CanService.select_posstationS("20190417111411","SEWUFFNAJDK","0002");
		if(posstation!=null){

			logger.info("posstation  "+ posstation.toString());
		}else {
			logger.info("posstation  空的啊");
		}
//		(List<tPublicSale_JingDong_z> tPublicSale_JingDong_zlist, List<tPublicSaleDetail_JingDong_z> tPublicSaleDetail_JingDong_zlist
//				,Double fVipRate,Double bDiscount,String tableName,posstation posstation)
		String result=CanService.getPreGoodsInfoS(list1,list,"100","1","",posstation,"");

		logger.info("获取的结果 result"+result);
	}

	@Test//得到品类打折促销
	public void gett_PloyOfSale_GoodsType(){
		List<t_PloyOfSale_GoodsType> list=CanService.gett_PloyOfSale_GoodsTypeS("000");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}

	@Test//得到自定义组合促销
	public void gett_PloyGroupOfSale(){
		List<t_PloyGroupOfSale> list=CanService.selectByNowDataS("%,"+"0002"+"%");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}
	//得到加密的key
	@Test
	public void getKey(){

		System.out.println(MD5Encode(appkey+"20190703164349","UTF-8"));

		System.out.println(MD5Encode(appkey+"ABCDEFGHIJKB","UTF-8").toUpperCase());
		System.out.println(MD5Encode(appkey+"ABCDEFGHIJKC","UTF-8").toUpperCase());
		System.out.println(MD5Encode(appkey+"ABCDEFGHIJKD","UTF-8").toUpperCase());
		//ac521996f1dcee978093afc8cb6f87e4
	}
	//下载促销商品
	@Test
	public void getCXGoods(){
		List<t_PloyOfSale> list=CanService.gett_t_PloyOfSaleS("0001");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}
	//下载会员商品
	@Test
	public void getVipGoods(){
		List<t_VipcGoodsPrice> list=CanService.gett_VipcGoodsPriceS("%,0001,%");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}
	}

	//得到key  签名   秘钥  废弃了
	@Test
	public void getSign() throws Exception {
		logger.info("我是签名类"+": ");
		String abc=
				ThreeDESUtil.
						encrypt("20181211101847eyJleHAiOjE1NDI3OTE4NDU",
								"123456788765432112345678",
								"01234567");
		System.out.println(abc);

		System.out.println("我是真实的秘钥 "+abc.replaceAll("=","").replaceAll("\\+","").replaceAll("\\s*","").replaceAll("\\n","").trim());
	}
	@Test
	public void testGetGoodsKuCun(){

		List<String> list1=new ArrayList<String>();
		list1.add("1010000001");
		list1.add("1010000002");
		List<t_goodsKuCurQty_wei> list=CanService.get_t_goodsKuCurQty_weiS("000",list1);
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}

	}

	@Test
	public void getEndTime(){
		t_MingShengTime t_mingShengTime=mingSheng.get_t_MingShengTime("");
		logger.info(t_mingShengTime.toString());
	}
	@Test
	public void uploaddelete(){
	  	mingSheng.minshengCopy("");
	}

	@Test
	public void upload(){
		try {

			uploadMingShengOrderTrue("2018-10-30 10:23:20.0",
                    "2018-11-30 10:23:20.0","1","20",
                    "516","GJA6BC0ADF2EHUE3B1DC0LPC29CB56HK",
                    "2247","http://111.198.131.250:19600/router");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void uploadMingShengOrderTrue(String btn,String etn,String pageNum,
										 String pagenumber,String appid,
										 String miyao,String supplierid,String url) throws JSONException {

		Map<String, String> form = new HashMap<String, String>();
		form.put("appKey", appid);              //appId  "462"
		form.put("v", "1.0");
		form.put("method", "minshengec.supplier.order.get");
		form.put("format", "json");
		form.put("supplierId", supplierid);        //供应商id
		form.put("timestamp", String_Tool.DataBaseTime());
		form.put("orderStatus", "2");          //已完成的订单
		form.put("receiveType", "1");
		form.put("pageIndex", pageNum);
		form.put("pageSize", pagenumber);
		form.put("type", "0");
		form.put("beginDate", btn);//"2018-01-01 12:22:33"
		form.put("endDate", etn);
		String sign = RopUtils.sign(form, miyao);//"438F6241DD9511E4B9E70050568E0E81"
		form.put("sign", sign);
		String result =sendPost(url, form);//"http://111.198.131.250:19600/router"
		System.out.println(result);
		org.json.JSONObject json=null;
		try{
			json=new org.json.JSONObject(result);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("Json 转换错误");
			return;
		}

		if(!json.has("totalResults")){
			return;
		}
		if(json.get("orderInfos").toString().equals("null")){
			return;
		}
		int number=json.getInt("totalResults");

		System.out.println(json.getInt("totalResults"));
//        System.out.println(json.get("orderInfos"));
		JSONArray jsonArray=JSONArray.fromObject(json.get("orderInfos").toString());
//        System.out.println("是我"+jsonArray);
//        System.out.println("是我2"+jsonArray.get(0));

		List<t_MinShengPublicSale_z> t_MinShengPublicSale_z_list=JSONArray.toList(jsonArray,new t_MinShengPublicSale_z(),new JsonConfig());
		for(t_MinShengPublicSale_z t:t_MinShengPublicSale_z_list){
			if(t.getOfflineStoresId()==null){
				t.setOfflineStoresId("");
			}
			t.setBeginTime(btn.toString());
			t.setEndTime(etn.toString());
		}
		String str= listBean.getBeanJson(t_MinShengPublicSale_z_list);
		System.out.println(" 我是 t_MinShengPublicSale_z_list"+str);
		List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist=new ArrayList<t_MingShengPublicSaleDetail_z>();

		for(int i=0;i<jsonArray.size();i++){
			org.json.JSONObject jsonObject=new org.json.JSONObject(jsonArray.get(i).toString());
			String setOfflineStoresId="";
			String OrderSn=jsonObject.getString("orderSn");
			if(jsonObject.has("offlineStoresId")){
				setOfflineStoresId=jsonObject.getInt("offlineStoresId")+"";
			}
			// System.out.println(" 我是 totalPrice"+jsonObject.getDouble("totalPrice"));
			JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("orderItems").toString());

			List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist_item=
					JSONArray.toList(jsonArray2,new t_MingShengPublicSaleDetail_z(),new JsonConfig());

			for(t_MingShengPublicSaleDetail_z t:t_MingShengPublicSaleDetail_zlist_item){
				t.setOfflineStoresId(setOfflineStoresId);
				t.setOrderSn(OrderSn);
			}
			t_MingShengPublicSaleDetail_zlist.addAll(t_MingShengPublicSaleDetail_zlist_item);
//            for(int j=0;j<jsonArray2.size();j++){
//                JSONObject jsonObject1=new JSONObject(jsonArray2.get(j).toString());
//                System.out.println(" 我是 price"+jsonObject1.getDouble("price"));
//            }

		}
		str= listBean.getBeanJson(t_MingShengPublicSaleDetail_zlist);
		System.out.println("我是orderItems中的打印"+str);
		try{
			testInsertSaleMngsheng( t_MinShengPublicSale_z_list, t_MingShengPublicSaleDetail_zlist);
			//删除数据
			mingSheng.minshengCopy("");
		}catch (Exception e){
			e.printStackTrace();
			logger.error("民生订单下拉出错了");
		}
	  int AllpageNum=String_Tool.getAllPage(number,Integer.parseInt(pagenumber));
	  if(AllpageNum>0){
		  uploadMingShengOrderTrue_fenye( btn, etn, pageNum,
				   pagenumber, appid,
				   miyao, supplierid, url);
	  }

	}

	@Test
	public void getDay() throws ParseException {

		Calendar c = Calendar.getInstance();
		logger.info(" 时间       "+c);
		c.setTime(new Date());
		c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-01-04 13:49:40"));
		int day1 = c.get(Calendar.DATE);
		int HOUR=c.get(Calendar.HOUR);
		c.set(Calendar.DATE, day1 - 1);
		c.set(Calendar.HOUR, HOUR - 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		System.out.println(dayAfter);
		logger.info(" 时间       "+dayAfter);
	}

	//这里是真实的拉取数据
	@Test
	public void uploadTreu(){
		try {
			uploadMingShengOrderTrue_fenye(" 2019-01-03 00:00:00",
                    " 2019-01-05 00:00:00","1","50",
                    "946","C8C41C4A18675A74E01C8A20E8A0F662",
                    "946","http://api.minshengec.com/router");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void uploadMingShengOrderTrue_fenye(String btn,String etn,String pageNum,
										 String pagenumber,String appid,
										 String miyao,String supplierid,String url) throws JSONException {

		String num=pageNum;
		int k=1;
		while (true){

			logger.info("我是第"+k+"次循环"+" 循环的页数是"+num);
			k=k+1;
			Map<String, String> form = new  HashMap<String, String>();
			form.put("appKey", appid);              //appId  "462"
			form.put("v", "1.0");
			form.put("method", "minshengec.supplier.order.get");
			form.put("format", "json");
			form.put("supplierId", supplierid);        //供应商id
			form.put("timestamp", String_Tool.DataBaseTime());
			form.put("orderStatus", "2");          //已完成的订单
			form.put("receiveType", "1");
			form.put("pageIndex", num);
			form.put("pageSize", pagenumber);
			form.put("type", "0");
			form.put("beginDate", btn);//"2018-01-01 12:22:33"
			form.put("endDate", etn);
			String sign = RopUtils.sign(form, miyao);//"438F6241DD9511E4B9E70050568E0E81"
			form.put("sign", sign);
			String result =sendPost(url, form);//"http://111.198.131.250:19600/router"
			if(result.equals("")){
				logger.error("获取民生商城的订单的数据异常");
				break;
			}
			//System.out.println(result);
			org.json.JSONObject json=null;
			try{
				json=new org.json.JSONObject(result);
			}catch (Exception e){
				e.printStackTrace();
				logger.error("Json 转换错误");
				break;
			}
			//没有这个值
			if(!json.has("totalResults")){
				logger.error("获取的数据异常");
				break;
			}
			int number=json.getInt("totalResults");//orderInfos
			if(json.get("orderInfos").toString().equals("null")){
				break;
			}
			System.out.println(json.getInt("totalResults"));
			JSONArray jsonArray=JSONArray.fromObject(json.get("orderInfos").toString());
			List<t_MinShengPublicSale_z> t_MinShengPublicSale_z_list=JSONArray.toList(jsonArray,new t_MinShengPublicSale_z(),new JsonConfig());
			for(t_MinShengPublicSale_z t:t_MinShengPublicSale_z_list){
				if(t.getOfflineStoresId()==null){
					t.setOfflineStoresId("");
				}
				t.setBeginTime(btn.toString());
				t.setEndTime(etn.toString());
			}
			//String str= listBean.getBeanJson(t_MinShengPublicSale_z_list);
			//System.out.println(" 我是 t_MinShengPublicSale_z_list"+str);
			List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist=new ArrayList<t_MingShengPublicSaleDetail_z>();

			for(int i=0;i<jsonArray.size();i++){
				org.json.JSONObject jsonObject=new org.json.JSONObject(jsonArray.get(i).toString());
				String setOfflineStoresId="";
				String OrderSn=jsonObject.getString("orderSn");
				if(jsonObject.has("offlineStoresId")){
					setOfflineStoresId=jsonObject.getInt("offlineStoresId")+"";
				}

				JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("orderItems").toString());

				List<t_MingShengPublicSaleDetail_z>  t_MingShengPublicSaleDetail_zlist_item=
						JSONArray.toList(jsonArray2,new t_MingShengPublicSaleDetail_z(),new JsonConfig());

				for(t_MingShengPublicSaleDetail_z t:t_MingShengPublicSaleDetail_zlist_item){
					t.setOfflineStoresId(setOfflineStoresId);
					t.setOrderSn(OrderSn);
					if(t.getWeight()==null){
						t.setWeight(0.0);
					}
					if(t.getFullName()==null){
						t.setFullName("");
					}
					if(t.getQuantity()==null){
						t.setQuantity(0.0);
					}
				}
				t_MingShengPublicSaleDetail_zlist.addAll(t_MingShengPublicSaleDetail_zlist_item);

			}
			//str= listBean.getBeanJson(t_MingShengPublicSaleDetail_zlist);
			//System.out.println("我是orderItems中的打印"+str);
			try{
				testInsertSaleMngsheng( t_MinShengPublicSale_z_list, t_MingShengPublicSaleDetail_zlist);
			}catch (Exception e){
				e.printStackTrace();
				logger.error("民生订单下拉出错了");
				throw new RuntimeException("插入数据库出错");
			}
			num=String.valueOf(Integer.parseInt(num)+1);
			mingSheng.minshengCopy("");
		}


	}

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			timeout=36000,
			rollbackFor={RuntimeException.class, Exception.class},
			readOnly = false)
	public void testInsertSaleMngsheng(List t_MinShengPublicSale_z_list,List t_MingShengPublicSaleDetail_zlist){
		try{
			int a=mingSheng.insert_t_MingShengPublicSaleDetail_z(t_MingShengPublicSaleDetail_zlist);
			if(a>0){
				try{
					mingSheng.insert_t_MinShengPublicSale_z(t_MinShengPublicSale_z_list);
				}catch (Exception e){
					e.printStackTrace();
					throw new RuntimeException("插入数据库出错");
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("插入数据库出错");
		}

	}
	@Test
	public void testGetOrder(){
		logger.info(CanService.get_Order("10","abc"));
	}
	@Test//测试退单的接口
	public void testTuiDanTrue(){
		String jsonStr="{\n" +
				"\t\"sale\": [{\n" +
				"\t\t\"actualMoney\": \"25.30\",\n" +
				"\t\t\"saleTime\": \"2018-11-24 16:44:30\",\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"saleTypeName\": \"支付宝支付\",\n" +
				"\t\t\"saleType\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"discountMoney\": \"4.70\",\n" +
				"\t\t\"saleAllMoney\": \"30\",\n" +
				"\t\t\"cSheetNo\": \"08\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t \"returnSale\": \"1\",\n" +
				"\t\t\"returnTime\": \"2018-03-02 15:12:32\",\n" +
				"\t\t\"returncSheetNo\": \"02\",\n" +
				"\t\t\"beizhu\": \"退货\"\n" +
				"\t}],\n" +
				"\t\"saleDatial\": [{\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"08\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"0\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"08\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"1\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"08\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"2\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}]\n" +
				"}";
		String danhao="08";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("sale"));
		JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("saleDatial"));
		List<tPublicSaleDetail_z> list=JSONArray.toList(jsonArray2,new tPublicSaleDetail_z(),new JsonConfig());
		for(tPublicSaleDetail_z t:list){
			t.setcSheetNo(danhao);
		}
		String str= listBean.getBeanJson(list);
		logger.info("数据集合 tPublicSaleDetail_z"+str);
		List<returntPublicSale_z> list1=JSONArray.toList(jsonArray1,new returntPublicSale_z(),new JsonConfig());

		for(returntPublicSale_z t:list1){
			t.setcSheetNo(danhao);
		}

		str= listBean.getBeanJson(list1);
		logger.info("数据集合 tPublicSale_z"+str);

		String result=CanService.placing_ruturnorder(list1,list);

		logger.info("获取的结果 result"+result);
	}

	//测试下单
	@Test
	public void testXiadanTrue(){
		String jsonStr="{\n" +
				"\t\"sale\": [{\n" +
				"\t\t\"actualMoney\": \"25.30\",\n" +
				"\t\t\"saleTime\": \"2018-11-24 16:44:30\",\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"saleTypeName\": \"支付宝支付\",\n" +
				"\t\t\"saleType\": \"1\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"discountMoney\": \"4.70\",\n" +
				"\t\t\"saleAllMoney\": \"30\",\n" +
				"\t\t\"cSheetNo\": \"15430490706490\",\n" +
				"\t\t\"cWHno\": \"000\"\n" +
				"\t}],\n" +
				"\t\"saleDatial\": [{\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"1543051381028\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"0\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"1543051381028\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"1\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}, {\n" +
				"\t\t\"cStoreNo\": \"000\",\n" +
				"\t\t\"discountPrice\": \"19.8\",\n" +
				"\t\t\"cGoodsName\": \"测试商品1\",\n" +
				"\t\t\"discountMoney\": \"1.0\",\n" +
				"\t\t\"cSheetNo\": \"1543051381028\",\n" +
				"\t\t\"cWHno\": \"000\",\n" +
				"\t\t\"cGoodsNo\": \"111111111\",\n" +
				"\t\t\"fNormalPrice\": \"20.36\",\n" +
				"\t\t\"bAuditing\": \"0\",\n" +
				"\t\t\"iSeed\": \"2\",\n" +
				"\t\t\"fQuantity\": \"12\",\n" +
				"\t\t\"appId\": \"abc\",\n" +
				"\t\t\"cBarcode\": \"555555555\"\n" +
				"\t}]\n" +
				"}";
		String danhao="09";
		jsonStr=jsonStr.replaceAll("\\*","").replaceAll("\\n","").replaceAll("\\t","");

		logger.info("数据集合 jsonStr"+jsonStr);

		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		JSONArray jsonArray1=JSONArray.fromObject(jsonObject.get("sale"));
		JSONArray jsonArray2=JSONArray.fromObject(jsonObject.get("saleDatial"));
		List<tPublicSaleDetail_z> list=JSONArray.toList(jsonArray2,new tPublicSaleDetail_z(),new JsonConfig());
		for(tPublicSaleDetail_z t:list){
			t.setcSheetNo(danhao);
		}
		String str= listBean.getBeanJson(list);
		logger.info("数据集合 tPublicSaleDetail_z"+str);
		List<tPublicSale_z> list1=JSONArray.toList(jsonArray1,new tPublicSale_z(),new JsonConfig());

		for(tPublicSale_z t:list1){
			t.setcSheetNo(danhao);
		}

		str= listBean.getBeanJson(list1);
		logger.info("数据集合 tPublicSale_z"+str);

		String result=CanService.placing_order(list1,list);

		logger.info("获取的结果 result"+result);
	}

	@Test
	public void testXiadan(){
		List<tPublicSaleDetail_z> list=new ArrayList<tPublicSaleDetail_z>();
		tPublicSaleDetail_z tPublicSaleDetail_z=null;
		String danhao="03";
		for(int i=0;i<5;i++){
			tPublicSaleDetail_z=new  tPublicSaleDetail_z();
			String time=DataBaseTime();
			logger.info("当前的时间是  "+time);
			tPublicSaleDetail_z.setcSheetNo(danhao);
			tPublicSaleDetail_z.setAppId("abc");
			tPublicSaleDetail_z.setcStoreNo("000");
			tPublicSaleDetail_z.setcWHno("000");
			tPublicSaleDetail_z.setbAuditing("0");
			tPublicSaleDetail_z.setcGoodsNo("111111111");
			tPublicSaleDetail_z.setcGoodsName("测试商品1");
			tPublicSaleDetail_z.setcBarcode("555555555");
			tPublicSaleDetail_z.setfNormalPrice("20.36");//20.36
			tPublicSaleDetail_z.setDiscountPrice("19.8");
			tPublicSaleDetail_z.setDiscountMoney("1.0");
			tPublicSaleDetail_z.setiSeed(""+i);
			tPublicSaleDetail_z.setfQuantity("12");
			list.add(tPublicSaleDetail_z);
		}

		String str= listBean.getBeanJson(list);
		logger.info("数据集合 tPublicSaleDetail_z"+str);

		List<tPublicSale_z> list1=new ArrayList<tPublicSale_z>();
		tPublicSale_z tPublicSale_z=null;
		for(int i=0;i<1;i++){
			tPublicSale_z=new  tPublicSale_z();
			String time=DataBaseTime();
			logger.info("当前的时间是  "+time);
			tPublicSale_z.setcSheetNo(danhao);
			tPublicSale_z.setSaleTime(time);
			tPublicSale_z.setSaleType("1");
			tPublicSale_z.setSaleTypeName("支付宝支付");
			tPublicSale_z.setSaleAllMoney("30");
			tPublicSale_z.setActualMoney("25.30");
			tPublicSale_z.setDiscountMoney("4.70");
			tPublicSale_z.setAppId("abc");
			tPublicSale_z.setcStoreNo("000");
			tPublicSale_z.setcWHno("000");
			list1.add(tPublicSale_z);
		}
		str= listBean.getBeanJson(list1);
		logger.info("数据集合 tPublicSale_z"+str);

		String result=CanService.placing_order(list1,list);

		logger.info("获取的结果 result"+result);
	}

	@Test
	public void testAddtPublicSaleDetail_z(){

		List<tPublicSaleDetail_z> list=new ArrayList<tPublicSaleDetail_z>();
		tPublicSaleDetail_z tPublicSale_z=null;
		String daanhao=System.currentTimeMillis()+"";
		for(int i=0;i<3;i++){
			tPublicSale_z=new  tPublicSaleDetail_z();
			String time=DataBaseTime();
			logger.info("当前的时间是  "+time);
			tPublicSale_z.setcSheetNo(daanhao);
			tPublicSale_z.setAppId("abc");
			tPublicSale_z.setcStoreNo("000");
			tPublicSale_z.setcWHno("000");
			tPublicSale_z.setbAuditing("0");
			tPublicSale_z.setcGoodsNo("111111111");
			tPublicSale_z.setcGoodsName("测试商品1");
			tPublicSale_z.setcBarcode("555555555");
			tPublicSale_z.setfNormalPrice("20.36");
			tPublicSale_z.setDiscountPrice("19.8");
			tPublicSale_z.setDiscountMoney("1.0");
			tPublicSale_z.setiSeed(""+i);
			tPublicSale_z.setfQuantity("12");
			list.add(tPublicSale_z);
		}
		String str= listBean.getBeanJson(list);
		logger.info("数据集合 "+str);

		int count=CanDao.insert_tPublicSaleDetail_z(list);
		logger.info("影响的行数是几行 "+count);



	}
	@Test
	public void testJsonToProj(){

		String str="[{\n" +
				"\t\"actualMoney\": \"25.30\",\n" +
				"\t\"saleTime\": \"2018-11-24 16:44:30\",\n" +
				"\t\"cStoreNo\": \"000\",\n" +
				"\t\"saleTypeName\": \"支付宝支付\",\n" +
				"\t\"saleType\": \"1\",\n" +
				"\t\"appId\": \"abc\",\n" +
				"\t\"discountMoney\": \"4.70\",\n" +
				"\t\"saleAllMoney\": \"30\",\n" +
				"\t\"cSheetNo\": \"15430490706490\",\n" +
				"\t\"cWHno\": \"000\"\n" +
				"}]";
		JSONArray jsonObject=JSONArray.fromObject(str.replaceAll("\\s*","").replaceAll("\\n",""));
		logger.info("json            "+jsonObject);

		List<tPublicSale_z> list=JSONArray.toList(jsonObject,new tPublicSale_z(),new JsonConfig());

		logger.info("tPublicSale_z            "+list.toString());

	}
	@Test
	public void testAddtPublicSale_z(){

		List<tPublicSale_z> list=new ArrayList<tPublicSale_z>();
		tPublicSale_z tPublicSale_z=null;
        for(int i=0;i<19;i++){
			tPublicSale_z=new  tPublicSale_z();
			String time=DataBaseTime();
			logger.info("当前的时间是  "+time);
			tPublicSale_z.setcSheetNo(System.currentTimeMillis()+""+i);
			tPublicSale_z.setSaleTime(time);
			tPublicSale_z.setSaleType("1");
			tPublicSale_z.setSaleTypeName("支付宝支付");
			tPublicSale_z.setSaleAllMoney("30");
			tPublicSale_z.setActualMoney("25.30");
			tPublicSale_z.setDiscountMoney("4.70");
			tPublicSale_z.setAppId("abc");
			tPublicSale_z.setcStoreNo("000");
			tPublicSale_z.setcWHno("000");
			list.add(tPublicSale_z);
		}
		String str= listBean.getBeanJson(list);
		logger.info("数据集合 "+str);

		int count=CanDao.insert_tPublicSale_z(list);
		logger.info("影响的行数是几行 "+count);



	}

	@Test
	public void testGetCangKu(){
		List<tWareHouse> list=CanService.get_tWareHouseS("000");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}

	}

	@Test
	public void testGetGoods(){
		List<cGoods> list=CanService.get_cGoodsS("0002","1","3");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}

	}


	@Test
	public void getUserService() {
		String string="{\n" +
				"\t\"table\": \"item_info\",\n" +
				"\t\"columns\": [\"brand_no\", \"id\", \"item_clsno\", \"item_name\", \"item_no\", \"item_size\", \"item_subno\", \"oper_date\", \"price\", \"sale_price\", \"supplier_no\", \"unit_no\", \"vip_price\"],\n" +
				"\t\"rows\": [\n" +
				"\t\t[\"0155\", \"45478\", \"010101\", \"汉臣氏益生菌粉(四联菌)10 袋装\", \"6946044309645\", \"\", \"01020572\", \"2017-03-09 11:47:10\", \"0.10\", \"68\", \"129\", \"\", \"0\"]\n" +
				"\t]\n" +
				"}";

		logger.info("获取的数据   "+Arrays.asList(string).toString());
		Arrays.asList(string).toString();

		List<tThirdUsers> list=UserService.get_tThirdUsersS("abc");
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}


	}

	/**
	 * 得到促销特价商品
	 */
	@Test
	public void getPloyOfSale() {

		List<tPloyOfSale> list=CanService.get_tPloyOfSaleS("0002","6928804011258",1);
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}


	}

	@Test
	public void testImportSales(){
		String string="{\n" +
				"\t\"orderData\": {\n" +
				"\t\t\"storecode\":\"0012\",\n" +
				"\t\t\"cardno\":\"12\",\n" +
				"\t\t\"orderid\"：\"123\",\n" +
				"\t\t\"goodsamount\": \"0.40\",\n" +
				"\t\t\"goodssum\": 3,\n" +
				"\t\t\"goodspayamount\": \"0.40\",\n" +
				"\t\t\"discount\": \"0.00\",\n" +
				"\t\t\"goodsdatas\": [{\n" +
				"\t\t\t\"GoodsSpec\": \"1*1\",\n" +
				"\t\t\t\"IsWholePack\": 0,\n" +
				"\t\t\t\"Img\": \"\",\n" +
				"\t\t\t\"BarCode\": \"2231470000032\",\n" +
				"\t\t\t\"Num\": 1,\n" +
				"\t\t\t\"GoodsCode\": \"3147000003\",\n" +
				"\t\t\t\"YouHuiAmount\":\"\",\n" +
				"\t\t\t\"SalePrice\": 0.2,\n" +
				"\t\t\t\"PackRate\": 1,\n" +
				"\t\t\t\"GoodsName\": \"大号购物袋\",\n" +
				"\t\t\t\"Unit\":\"\",\n" +
				"\t\t\t\"goodsamount\": 0.2,\n" +
				"\t\t\t\"OldSalePrice\":\"\"\n" +
				"\t\t}]\n" +
				"\t},\n" +
				"\t\"payData\": {\n" +
				"\t\t\"success\": true,\n" +
				"\t\t\"obj\": {\n" +
				"\t\t\t\"alipay_trade_pay_response\": {\n" +
				"\t\t\t\t\"code\": \"10000\",\n" +
				"\t\t\t\t\"msg\": \"Success\",\n" +
				"\t\t\t\t\"buyer_logon_id\": \"136***@163.com\",\n" +
				"\t\t\t\t\"buyer_pay_amount\": \"0.40\",\n" +
				"\t\t\t\t\"buyer_user_id\": \"2088002429412534\",\n" +
				"\t\t\t\t\"fund_bill_list\": [{\n" +
				"\t\t\t\t\t\"amount\": \"0.40\",\n" +
				"\t\t\t\t\t\"fund_channel\": \"PCREDIT\"\n" +
				"\t\t\t\t}],\n" +
				"\t\t\t\t\"gmt_payment\": \"2018-05-12 15:30:19\",\n" +
				"\t\t\t\t\"invoice_amount\": \"0.40\",\n" +
				"\t\t\t\t\"out_trade_no\": \"3791100034995204502141394944\",\n" +
				"\t\t\t\t\"point_amount\": \"0.00\",\n" +
				"\t\t\t\t\"receipt_amount\": \"0.40\",\n" +
				"\t\t\t\t\"store_name\": \"精品超市(长申店)\",\n" +
				"\t\t\t\t\"total_amount\": \"0.40\",\n" +
				"\t\t\t\t\"trade_no\": \"2018051221001004530531164774\"\n" +
				"\t\t\t},\n" +
				"\t\t\t\"msg\": \"支付成功\"\n" +
				"\t}";
	}
	@Test
	public void testJson(){
		String str="[\n" +
				"  {\"cardno\",\"\",\"storecode\":\" 0012 \",\"goodscode\":\"\",\"barcode\":\"123456\",\"num\":1,\"price\":1,\"oprice\":1},\n" +
				"  {\"cardno\",\"\",\"storecode\":\" 0012 \",\"goodscode\":\"\",\"barcode\":\"123456\",\"num\":1,\"price\":1,\"oprice\":1},\n" +
				"  {\"cardno\",\"\",\"storecode\":\" 0012 \",\"goodscode\":\"\",\"barcode\":\"123456\",\"num\":1,\"price\":1,\"oprice\":1}\n" +
				"]";
		try{
			JSONArray array=JSONArray.fromObject(str);
			for(int i=0;i<array.size()-1;i++){
				JSONObject jsonObject=new JSONObject();
				jsonObject=array.getJSONObject(i);
				System.out.println(jsonObject);
			}
		}catch (Exception e){

			System.out.println("json解析出错");
			//logger.error("json解析出错");
		}




	}
	@Test
	public void testException() {//测试特殊符号分割
		//System.out.println(1/0);

		logger.error("XXX {} {} ", new String[]{"123","456"});
	}
	@Test
	public void testTE() {//测试特殊符号分割
		String barcodeS="6928804011258,6928804011159,6925788301269,6925788301269";//"6928804011258|6928804011159"
		List<String> stringList=new ArrayList<String>();
		if(barcodeS.contains(",")){
			String[] arr = barcodeS.split(","); // 用,分割

			for(String barcode:arr){
				stringList.add(barcode);
			}
			logger.info("我包含|"+stringList.toString());
		}else{
			stringList.add(barcodeS);
			logger.info("我不包含|"+stringList.toString());
		}
	}
	@Test
	public void getcStoreGoods() {

		//6928804011258|6928804011159|||6925788301269|6925788301269
		String barcodeS="|||||||";//"6928804011258|6928804011159"
		List<String> stringList=new ArrayList<String>();
		if(barcodeS.contains("|") && !barcodeS.trim().equals("|")){
			String[] arr =null;
			try{
				arr= barcodeS.split("\\|");
				for(String barcode:arr){
					stringList.add(barcode);
				}
				logger.info("我包含|"+stringList.toString());

			}catch (Exception e){
				stringList.add(barcodeS);
				logger.error("我是出错的数据|"+stringList.toString());
			}



		}else{
			stringList.add(barcodeS);
			logger.info("我不包含|"+stringList.toString());
		}
        if(stringList.isEmpty()){
			stringList.add("");
		}
		List<cStoreGoods> list=CanService.get_cStoreGoodsS("000",stringList);
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}


	}
	@Test
	public void getVip() {

		List<vip> list=CanService.get_vipS("13628762212");//"23423423423"
		logger.info("获取的数据"+list.size());
		if(list!=null && !list.isEmpty()){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}


	}

	@Test
	public void getStore() {

		List list2=null;

		System.out.println(list2);

		if(list2==null || list2.toString().equals("[]") ){
			logger.info("测试短路的信息"+true);
		}
		List list1=new ArrayList();
		System.out.println(list1.toString());
		List<Store> list=CanService.get_cStroeS("000");
		if(list==null || list.toString().equals("[]")){
			String str= listBean.getBeanJson(list);
			logger.info("获取的数据"+str);
		}else {
			logger.info("获取的数据空的数据集"+list);
		}


	}

	@Test
	public void contextLoads() {

		List list2=null;

		System.out.println(list2);
		//System.out.println(list2.toString());  报错  下面不报错   找原因
		//这个测试需要注意一下   这里需要利用短路信息
		if(list2==null || list2.toString().equals("[]") ){
			logger.info("测试短路的信息"+true);
		}

		List list1=new ArrayList();
		System.out.println(list1.toString());
		List<test1> list=testService.getTestsS();
		String str= listBean.getBeanJson(list);
		logger.info("获取的数据"+str);

		Gson gson = new Gson();
		String json = gson.toJson(list);
		logger.info("获取的数据不同的解析数据方式"+json);

	}

	@Test
	public void testredisTemplate() {

		redisTemplate.opsForValue().set("first","张明阳");

		redisTemplate.opsForValue().set("first", "张明阳111", 60, TimeUnit.SECONDS);

	}

	@Test
	public void testRedis(){
		logger.info("判断是否存在key值："+redisTemplate.hasKey(""));
		logger.info("得到key值："+redisTemplate.opsForValue().get("first"));
		logger.info("判断key值==null："+(redisTemplate.opsForValue().get("first")==null));

	}

}
