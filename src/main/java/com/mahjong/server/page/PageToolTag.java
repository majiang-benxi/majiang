package com.mahjong.server.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PageToolTag extends SimpleTagSupport {
	
	private String curPage;
	private String totalPage;
	private String jsAction;
	
	private int SHOW_PAGE_NUM = 6;
	
	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getJsAction() {
		return jsAction;
	}

	public void setJsAction(String jsAction) {
		this.jsAction = jsAction;
	}

	@SuppressWarnings("unused")
	public void doTag() throws JspException, IOException {
		 if (new Integer(curPage) == null) {
	            throw new JspException("缺乏curPage属性！");
	        }
		 if (new Integer(totalPage) == null) {
			 throw new JspException("缺乏totalPage属性！");
		 }
		 if (jsAction == null) {
			 throw new JspException("缺乏jsAction属性！");
		 }
		 
		 int[] showPages = getShowPages(Integer.parseInt(totalPage), Integer.parseInt(curPage));
		 
		 int cp = Integer.parseInt(curPage);
		 int tp = Integer.parseInt(totalPage);
		 
		 StringBuffer html = new StringBuffer("<div class=\"pagination\"><a href=\"#\" onclick=\""+jsAction+"('"+1+"','"+totalPage+"');\" class=\"next\">首页</a>");
		 if(cp==1)
		 {
			 html.append("<a href=\"#\" class=\"next\" >上一页</a>");
		 }else{
			 html.append("<a href=\"#\" class=\"next\" onclick=\""+jsAction+"('"+(Integer.parseInt(curPage)-1)+"','"+totalPage+"');\" >上一页</a>");
		 }
		  for (int i = 0;i< showPages.length;i++) {
			  if((showPages[i])==cp){
				  html.append("<a href=\"#\" class=\"cur\">"+showPages[i]+"</a>");
			  }else{
				  html.append("<a href=\"#\" onclick=\""+jsAction+"('"+showPages[i]+"','"+totalPage+"');\">"+showPages[i]+"</a>");
			  }
		  }
		 if(cp==tp){
			 html.append("<a href=\"#\" class=\"next\">下一页</a>");
		 }else{
			 html.append("<a href=\"#\" class=\"next\" onclick=\""+jsAction+"('"+(Integer.parseInt(curPage)+1)+"','"+totalPage+"');\" >下一页</a>");
		 }
		 html.append("<a href=\"#\" onclick=\""+jsAction+"('"+totalPage+"','"+totalPage+"');\" class=\"next\">尾页</a>");
		 html.append("</div>");
    
      getJspContext().getOut().println(html.toString());

		 
	}
	
	 private int[] getShowPages(int totalPages,int currentPage){
	    	
	    	int[] showPages = null;
	    	if(totalPages<=SHOW_PAGE_NUM){
	    		showPages = new int[totalPages];
	    		for (int i = 0; i < showPages.length; i++) {
	    			showPages[i] = (i+1);
				}
	    		return showPages;
	    	}else{
	    		
	    		int curPageCount = (currentPage%SHOW_PAGE_NUM)==0?currentPage/SHOW_PAGE_NUM:(currentPage/SHOW_PAGE_NUM+1);
	    		int totalPageCount = (totalPages%SHOW_PAGE_NUM==0)?totalPages/SHOW_PAGE_NUM:(totalPages/SHOW_PAGE_NUM+1);
	    		
	    		if(curPageCount==totalPageCount){
	    			showPages = new int[(totalPages%SHOW_PAGE_NUM==0)?SHOW_PAGE_NUM:totalPages%SHOW_PAGE_NUM];
		    		for (int i = 0; i < showPages.length; i++) {
		    			showPages[i] = (curPageCount-1)*SHOW_PAGE_NUM+(i+1);
					}
	    		}else if(curPageCount<totalPageCount){
	    			showPages = new int[SHOW_PAGE_NUM];
		    		for (int i = 0; i < showPages.length; i++) {
		    			showPages[i] = (curPageCount-1)*(SHOW_PAGE_NUM)+(i+1);
					}
	    		}
	    		return showPages;
	    	}
	    }

	
	
}
