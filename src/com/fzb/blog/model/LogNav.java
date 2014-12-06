 package com.fzb.blog.model;
 
 import com.fzb.common.util.ParseTools;
import com.jfinal.plugin.activerecord.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 public class LogNav extends Model<LogNav>
 {
   public static final LogNav dao = new LogNav();
 
   public List<LogNav> queryAll() {
     String sql = "select l.navId as id,l.navName,l.url,l.sort from  logNav l order by sort";
     return find(sql);
   }
   
   public Map<String,Object> queryAll(Integer page,Integer pageSize)
   {
	   Map<String,Object> data=new HashMap<String,Object>();
	   data.put("rows", find("select l.navId as id,l.navName,l.url,l.sort from  logNav l order by sort limit ?,?", new Object[] { Integer.valueOf(ParseTools.getFirstRecord(page, pageSize)), Integer.valueOf(pageSize) }));
       fillData(page, pageSize, "from logNav", data, new Object[0]);
       return data;
   }
   
   private void fillData(int page, int pageSize, String where, Map<String, Object> data, Object[] obj)
   {
     if (((List<Link>)data.get("rows")).size() > 0) {
       data.put("page", Integer.valueOf(page));
       long count = ((LogNav)findFirst("select count(1) cnt " + where, obj)).getLong("cnt").longValue();
       data.put("total", Integer.valueOf(ParseTools.getTotalPate(count, pageSize)));
       data.put("records", Long.valueOf(count));
     }
     else {
       data.clear();
     }
   }
 }
