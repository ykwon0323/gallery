package com.hk1.gallery;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.hk1.gallery.dto.CallendarDto;
import com.hk1.gallery.dto.DabgeulDto;
import com.hk1.gallery.dto.ExhibitionDto;
import com.hk1.gallery.dto.GalleryDto;
import com.hk1.gallery.dto.MemberDto;
import com.hk1.gallery.service.ICallendarService;
import com.hk1.gallery.service.IDabgeulService;
import com.hk1.gallery.service.IGalleryService;
import com.hk1.gallery.service.IMemberService;
import com.hk1.utils.Util;


/**
 * Handles requests for the application home page.
 * 
 * 
 * 
 * 160 갤러리 업로드 시 "D:/PROJECT/galleryupload/"에서 저장된 사진들 프로젝트에 galleryupload 파일에 옮겨놔야함 
 * 399 갤러리 업로드 시 "D:/PROJECT/galleryupload/"에서 저장된 사진들 프로젝트에 galleryupload 파일에 옮겨놔야함 
 */
@Controller
public class HomeController4 {

	@Autowired
	private IGalleryService galleryService;
	
	
	@Autowired
	private IDabgeulService dabgeulService;
	
	@Autowired
	private ICallendarService callendarService;
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired 
	private JavaMailSenderImpl mailSender;
	
	private Util util =new Util();


	private static final Logger logger = LoggerFactory.getLogger(HomeController4.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	/*갤러리 리스트 뽑기*/
	
	@RequestMapping(value = "/gallerylist.do", method = RequestMethod.GET)
	public String gallerylist(Locale locale, Model model,HttpSession session) {
		logger.info("gallerylist 가자 {}.", locale);

		List<GalleryDto> list = galleryService.selectGalleryList();

		model.addAttribute("list",list);
		

		
		//최초 답글 리스트 뽑기
/*		List<DabgeulDto> Dlist = dabgeulService.selectDabgeulList();
		
		model.addAttribute("Dlist",Dlist);*/
		
		
		return "gallery/gallerylist";
	}
	
	//내 갤러리 볼때 mno로 볼때
	
	@RequestMapping(value = "/selectM_noGalleryList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String selectM_noGalleryList(Locale locale, Model model,HttpSession session) {
		logger.info("selectM_noGalleryList 가자 {}.", locale);

		//세션에 m_no  
				session.getAttribute("loginMember");
				
				MemberDto Ddto = (MemberDto) session.getAttribute("loginMember");
				
				int m_no =Ddto.getM_no();
				
				System.out.println("m_no로 볼때 m_no=" + m_no);
		
		List<GalleryDto> list = galleryService.selectM_noGalleryList(m_no);
		

		model.addAttribute("list",list);


		return "gallery/gallerylist_m_no";
	}
	
	/* 안쓸듯
	스테이트 3인 갤러리만 불러오자
	@RequestMapping(value = "/selectG_stateGalleryList.do", method = RequestMethod.GET)
	public String selectG_stateGalleryList(Locale locale, Model model,HttpSession session) {
		logger.info("selectG_stateGalleryList 가자 {}.", locale);

		세션에 g_state =3 이 담길것 임시 
		int g_state =3;
		
		List<GalleryDto> list = galleryService.selectG_stateGalleryList(g_state);

		model.addAttribute("list",list);
		
		

		return "gallery/gallerylist";
	}	
	*/



	@RequestMapping(value = "/insertGalleryForm.do", method = RequestMethod.GET)
	public String insertGalleryForm(Locale locale, Model model) {
		logger.info("insertGalleryForm 가자 {}.", locale);

		return "gallery/insertGalleryForm";

	}


	@RequestMapping(value = "/insertGallery.do", method = RequestMethod.POST)
	public String insertGallery(Locale locale, Model model, MultipartHttpServletRequest mtfRequest,
			HttpSession session)  {
		logger.info("insertGallery 가자 {}.", locale);

		

		GalleryDto galleryDto = new GalleryDto();
		
		
		String g_name = mtfRequest.getParameter("g_name");
		String g_intro = mtfRequest.getParameter("g_intro");
		String g_adress = mtfRequest.getParameter("g_adress");
		String g_tel = mtfRequest.getParameter("g_tel");
		String m_no = mtfRequest.getParameter("m_no");
		
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		
		
		//파일저장 경로
		String path = "D:/PROJECT/galleryupload/";

		
		
		
		
	/*	for (MultipartFile multifile : fileList) {
			
			//요청한 파일에서 원본 파일명 구하기
			String orign_fname = multifile.getOriginalFilename();
			
			String saveDirectory = path + orign_fname;
			
			try {
				multifile.transferTo(new File(saveDirectory));//파일객체의 경로대로 업로드 실행
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
		
		for (int i = 0; i < fileList.size(); i++) {
			
			String imgdbname= fileList.get(i).getOriginalFilename();
			
			String stored_fname= createUUId()
					+(imgdbname.substring(imgdbname.lastIndexOf(".")));

			String saveDirectory = path + stored_fname;
			
			try {
				
				fileList.get(i).transferTo(new File(saveDirectory));//파일객체의 경로대로 업로드 실행
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
			if (i==0) {
				galleryDto.setG_img1(stored_fname);
			}else if (i==1) {
				galleryDto.setG_img2(stored_fname);
			}else if (i==2) {
				galleryDto.setG_img3(stored_fname);
			}else if (i==3) {
				galleryDto.setG_img4(stored_fname);
			}
		}
		
		galleryDto.setG_name(g_name);
		galleryDto.setG_intro(g_intro);
		galleryDto.setG_adress(g_adress);
		galleryDto.setG_tel(g_tel);
		galleryDto.setM_no(Integer.parseInt(m_no));
		
		System.out.println("dto = ?" + galleryDto);
		boolean isS = galleryService.insertGallery(galleryDto);

/*		if(isS) {
			
			return "redirect:privatemain.do";
		}else {
			logger.info("파일 업로드 실패");
			return "redirect:insertGalleryForm.do";
		}*/
		
		MemberDto mDto = new MemberDto();
		String m_grade = mtfRequest.getParameter("m_grade");
		String m_id = mtfRequest.getParameter("m_id");
		String m_name = mtfRequest.getParameter("m_name");
		String m_address = mtfRequest.getParameter("m_address");
		String m_tel = mtfRequest.getParameter("m_tel");
		
		
		System.out.println("mdto1211 = ?" + mDto);
		
		
		
		mDto.setM_address(m_address);
		mDto.setM_grade(Integer.parseInt(m_grade));
		mDto.setM_id(m_id);
		mDto.setM_name(m_name);
		mDto.setM_no(Integer.parseInt(m_no));
		mDto.setM_tel(m_tel);
		System.out.println("mdto = ?" + mDto);
		
		
		
		
			
			boolean isM =memberService.updateMember(mDto);
			
			session.removeAttribute("loginMember");
			session.setAttribute("loginMember", mDto);
			System.out.println("memberupdate 성공");
	
		
		
		if (isS) {
			
			if(m_grade.equals("1")||m_grade==null) {
				return "redirect:privatemain.do";
			}else {
				return "redirect:selectM_noGalleryList.do";
			}
			
			
		}else {
			return "redirect:insertGalleryForm.do";
		}
		
	      

	}

	
	//갤러리 상세
	@RequestMapping(value = "/selectGallery.do", method = RequestMethod.GET)
	public String selectGallery(Locale locale, Model model,
			HttpServletRequest request, int g_no, String year, String month,CallendarDto cddto,String g_return)  {
		logger.info("selectGallery 가자 {}.", locale);

		GalleryDto galleryDto = galleryService.selectGallery(g_no);

		model.addAttribute("galleryDto",galleryDto);

		System.out.println(galleryDto);
		
		//캘린더
		
		System.out.println("g_no="+g_no);
		
		
		
		
		if(year==null) {
			
			Calendar calendar = Calendar.getInstance();
			
			year = calendar.get(Calendar.YEAR)+"";
			month =calendar.get(Calendar.MONTH)+1+"";
			
			cddto.setC_start(year.substring(0, 2)+month);
			
			cddto.setG_no(g_no);
			
			model.addAttribute("cddto",cddto);
			
			
		}else {
			
			
			
			System.out.println(month);
			System.out.println(year);
			
		
			
			System.out.println(util.isTwo(month));
			
			System.out.println(year.substring(2,4)+util.isTwo(month));
			
			
			cddto.setC_start(year.substring(2, 4)+util.isTwo(month));
			

			
			cddto.setG_no(g_no);
			
			System.out.println("g_no=" + g_no);

			model.addAttribute("cddto",cddto);
			
			
		}
		

		List<CallendarDto> clist = callendarService.selectCallendarList(cddto);

		model.addAttribute("clist",clist);
		

	
		List<CallendarDto> cllist = callendarService.selectCallendarList_g_no(g_no);

		model.addAttribute("cllist",cllist);
	
		
		if(g_return==null) {
			return "gallery/selectGallery_general_one";
		}else if(g_return.equals("general_one")) {
			return "gallery/selectGallery_general_one";
		}else if(g_return.equals("general_two")){
			return "gallery/selectGallery_general_two";
		}else if(g_return.equals("one")){
			return "gallery/selectGallery_one";
		}else if(g_return.equals("two")){
			return "gallery/selectGallery_two";
		}else {
			return "gallery/selectGallery_general_one";
		}
		
		
		
	}





	@RequestMapping(value = "/updateGallery.do", method = RequestMethod.POST)
	public String updateGallery(Locale locale, Model model,  MultipartHttpServletRequest mtfRequest)  {
		logger.info("updateGallery 가자 {}.", locale);
		
		
		System.out.println("1");
	
		GalleryDto galleryDto = new GalleryDto();
		
		String g_name = mtfRequest.getParameter("g_name");
		String g_intro = mtfRequest.getParameter("g_intro");
		String g_adress = mtfRequest.getParameter("g_adress");
		String g_no = mtfRequest.getParameter("g_no");
		String m_no = mtfRequest.getParameter("m_no");
		String g_state  = mtfRequest.getParameter("g_state");
		String g_tel  = mtfRequest.getParameter("g_tel");
		
		galleryDto.setG_name(g_name);
		galleryDto.setG_intro(g_intro);
		galleryDto.setG_adress(g_adress);
		galleryDto.setG_no(Integer.parseInt(g_no));
		galleryDto.setM_no(Integer.parseInt(m_no));
		galleryDto.setG_state(Integer.parseInt(g_state));
		galleryDto.setG_tel(g_tel);
		
		System.out.println(galleryDto.toString());
		
		
		
		
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		
		
		//파일저장 경로   
		String path = "D:/PROJECT/galleryupload/";

		
		for (int i = 0; i < fileList.size(); i++) {
			
			String imgdbname= fileList.get(i).getOriginalFilename();
			
			String stored_fname= createUUId()
					+(imgdbname.substring(imgdbname.lastIndexOf(".")));

			String saveDirectory = path + stored_fname;
			
			try {
				
				fileList.get(i).transferTo(new File(saveDirectory));//파일객체의 경로대로 업로드 실행
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			
			if (i==0) {
				galleryDto.setG_img1(stored_fname);
			}else if (i==1) {
				galleryDto.setG_img2(stored_fname);
			}else if (i==2) {
				galleryDto.setG_img3(stored_fname);
			}else if (i==3) {
				galleryDto.setG_img4(stored_fname);
			}
		}
		
		
		System.out.println("2");
		boolean isS = galleryService.updateGallery(galleryDto);

		if(isS) {
			return "redirect:selectM_noGalleryList.do";
		}else {
			logger.info("파일 업로드 실패");
			return "error";
		}

	}
	
	@RequestMapping(value = "/deleteGallery.do", method = RequestMethod.GET)
	public String deleteGallery(Locale locale, Model model, GalleryDto galleryDto, int g_no)  {
		logger.info("deleteGallery 가자 {}.", locale);
		

		boolean isS = galleryService.deleteGallery(g_no);

		if(isS) {
			return "redirect:selectM_noGalleryList.do";
		}else {
			logger.info("파일 업로드 실패");
			return "error";
		}

	}
	
	
	
	
	
	//답글을 달아보자 ////
	

	//답글 쓰기 관련
/*	@ResponseBody
	@RequestMapping(value = "/DabgeulInsertAjax.do", method = RequestMethod.POST)
	public Map<String, List<DabgeulDto>> DabgeulInsertAjax(Locale locale, Model model, int e_no, String d_content, int m_no, String m_name)  {
		logger.info("DabgeulInsertAjax 가자 {}.", locale);
	
		System.out.println(e_no);
		
		System.out.println(d_content);
		System.out.println(m_no);
		System.out.println(e_no);

		
		DabgeulDto dto = new DabgeulDto();
		
		dto.setE_no(e_no);
		dto.setD_content(d_content);
		dto.setM_no(m_no);
		dto.setM_name(m_name);


		boolean isS =  dabgeulService.InsertDabgeul(dto);

//		if(isS) {
			List<DabgeulDto> Dlist = dabgeulService.selectDabgeulList();
			Map<String, List<DabgeulDto>> map=new HashMap<String, List<DabgeulDto>>();
			map.put("dlist", Dlist);
			
			return map;
			
//		}else {
//			logger.info("파일 업로드 실패");
//			Map<String, String>map=new HashMap<String, String>();
//			map.put("dlist", "파일 업로드 실패");
//			return map;
//		
//		}
		
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/DabgeulInsertAjax.do", method ={RequestMethod.POST, RequestMethod.GET})
	public String DabgeulInsertAjax(Locale locale, Model model, int e_no, String d_content, int m_no, String m_name)  {
		logger.info("DabgeulInsertAjax 가자 {}.", locale);
	
		System.out.println(e_no);
		
		System.out.println(d_content);
		System.out.println(m_no);
		System.out.println(e_no);
	

	  
		
	/*	session.removeAttribute("Dlist");
		
		List<DabgeulDto> Dlist = dabgeulService.selectDabgeulList();
		
		session.setAttribute("Dlist",Dlist);*/
		
		DabgeulDto dto = new DabgeulDto();
		
		dto.setE_no(e_no);
		dto.setD_content(d_content);
		dto.setM_no(m_no);
		dto.setM_name(m_name);


		boolean isS =  dabgeulService.InsertDabgeul(dto);

		if(isS) {

			
			return "y";
			
		}else {
			logger.info("파일 업로드 실패");

			return "n";
		
		}
		
	}
	

	
	@RequestMapping(value = "/DabgeulList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String DabgeulLisㅅ(Locale locale, Model model,HttpSession session, HttpServletRequest request)  {
		logger.info("DabgeulList 가자 {}.", locale);
		
		//로그인 세션 값  

		MemberDto loginMember = (MemberDto)session.getAttribute("loginMember"); 
		ExhibitionDto exhibition =(ExhibitionDto)request.getAttribute("exhibitionDto");
		
		
		DabgeulDto Ddto = new DabgeulDto();
		
		int m_no =loginMember.getM_no();
		int e_no=exhibition.getE_no();
		
		
		Ddto.setE_no(e_no);
		Ddto.setM_no(m_no);
		
		
		
		
/*		DabgeulDto Ddto = new DabgeulDto();
		int m_no =0;
		int e_no=3;
		Ddto.setE_no(e_no);
		Ddto.setM_no(m_no);
		session.setAttribute("Ddto",Ddto);
		*/
		
		//작품마다 댓글 단다  e_no 기준으로 박는다
/*		e_no = 3;
		System.out.println("e_no=" + e_no);*/
	
		//e_no3
		List<DabgeulDto> Dlist = dabgeulService.selectDabgeulList(e_no);
		
		model.addAttribute("Dlist",Dlist);
	
		
		return "gallery/dabgeul";
		
		
		
		

	}
	
	//캘린더 박아보자
	
/*
	@RequestMapping(value = "/calendar.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String calendar(Locale locale, Model model,String year, String month,CallendarDto cddto)  {
		logger.info("calendar 가자 {}.", locale);
		
				
			int g_no =2 ;

			if(year==null) {
				
				Calendar calendar = Calendar.getInstance();
				
				year = calendar.get(Calendar.YEAR)+"";
				month =calendar.get(Calendar.MONTH)+1+"";
				
				cddto.setC_start(year.substring(0, 2)+month);
				
				cddto.setG_no(g_no);
				
				
			}else {
				
				
				
				System.out.println(month);
				System.out.println(year);
				
			
				
				System.out.println(util.isTwo(month));
				
				System.out.println(year.substring(2,4)+util.isTwo(month));
				cddto.setC_start(year.substring(2, 4)+util.isTwo(month));
				
				cddto.setG_no(g_no);
				
				System.out.println("g_no=" + g_no);
				
				
				
			}
			
	
			List<CallendarDto> clist = callendarService.selectCallendarList(cddto);

			model.addAttribute("clist",clist);
			
			
		
	
		
		return "gallery/callendar";
		
		
		

	}*/
	
	@RequestMapping(value = "/insertformcalendar.do", method = {RequestMethod.GET})
	public String insertformcalendar(Locale locale, Model model,String g_no,int c_start_y,int c_start_m,int c_start_d,String c_return)  {
		logger.info("insertformcalendar 가자 {}.", locale);
		
		model.addAttribute("g_no", g_no);
		model.addAttribute("c_start_y",c_start_y);
		model.addAttribute("c_start_m",c_start_m);
		model.addAttribute("c_start_d",c_start_d);
		model.addAttribute("c_return", c_return);
		
		System.out.println(c_return);
		return "gallery/insertformcalendar";

	}
	
	@ResponseBody
	@RequestMapping(value = "/insertcallendar.do", method = RequestMethod.POST)
	public String insertcalendar(Locale locale, Model model,HttpServletRequest request, CallendarDto callendarDto,
			String c_start_y,String c_start_m,String c_start_d,
			String c_end_y,String c_end_m,String c_end_d, int g_no, String c_return
			)  {
		logger.info("insertcallendar 가자 {}.", locale);
		
		
	
		String c_start= c_start_y.substring(2, 4)+util.isTwo(c_start_m)+util.isTwo(c_start_d);
		String c_end = c_end_y.substring(2, 4)+util.isTwo(c_end_m)+util.isTwo(c_end_d);
		
		callendarDto.setC_end(c_end);
		callendarDto.setC_start(c_start);
		callendarDto.setG_no(g_no);

 
		
		 callendarService.insertCallendar(callendarDto);
		 
		 
			//	"<script>opener.parent.location.reload();"
         //      + "window.close();</script>";
			//	year="+c_start_y+"&month="+c_start_m+"&g_no="+g_no
		 System.out.println("aaa="+c_return);
		 
		 String	kk = "<script>opener.parent.location.href='selectGallery.do?year="+c_start_y+
							
							"&month="+c_start_m+"&g_no="+g_no+"&g_return=two"+ "';"
							
							
			                + "window.close();</script>";
				
					

		
					
		
		return kk;

	}
	
	
	@RequestMapping(value = "/updateformcalendar.do", method = {RequestMethod.GET})
	public String updateformcalendar(Locale locale, Model model,
			String c_start_y,String c_start_m,String c_start_d,String c_return,
			String c_no, int g_no)  {
		logger.info("updateformcalendar 가자 {}.", locale);

		
		System.out.println(c_start_y);
		System.out.println(c_start_m);
		System.out.println(c_start_d);
		System.out.println(c_return);
		
		model.addAttribute("g_no", g_no);
		model.addAttribute("c_start_y",c_start_y);
		model.addAttribute("c_start_m",c_start_m);
		model.addAttribute("c_start_d",c_start_d);
		model.addAttribute("c_return", c_return);
		model.addAttribute("c_no", c_no);
	

		return "gallery/calendalupdate";

	}
	
	@ResponseBody
	@RequestMapping(value = "/updatecallendar.do", method = RequestMethod.POST)
	public String updatecallendar(Locale locale, Model model,HttpServletRequest request, CallendarDto calendarDto,
			String c_start_y,String c_start_m,String c_start_d,
			String c_end_y,String c_end_m,String c_end_d, int g_no, String c_return,int c_no
			)  {
		logger.info("updatecallendar 가자 {}.", locale);
		

		
	
		String c_start= c_start_y.substring(2, 4)+util.isTwo(c_start_m)+util.isTwo(c_start_d);
		String c_end = c_end_y.substring(2, 4)+util.isTwo(c_end_m)+util.isTwo(c_end_d);
		
		calendarDto.setC_end(c_end);
		calendarDto.setC_start(c_start);

		
		System.out.println(calendarDto);

 
		
		 boolean isS =callendarService.updateCallendar(calendarDto);
		 
		 
		 String	kk = "<script>opener.parent.location.href='selectGallery.do?year="+c_start_y+
						
						"&month="+c_start_m+"&g_no="+g_no+"&g_return=two"+ "';"
						
						
		                + "window.close();</script>";

		 
			if (isS) {
			
				return kk;
				

			}else {
				return kk;
			}


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/forminsertcalendar2.do", method = {RequestMethod.GET})
	public String forminsertcalendar2(Locale locale, Model model)  {
		logger.info("forminsertcalendar2 가자 {}.", locale);
		
		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month =calendar.get(Calendar.MONTH)+1;
		int date = calendar.get(Calendar.DATE);
		
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);
		
		System.out.println(year);
		System.out.println(month);
		System.out.println(date);
		
		
		
		
		return "gallery/forminsertcalendar2";

	}

	
	@RequestMapping(value = "/calendallist.do", method = {RequestMethod.GET})
	public String calendallist(Locale locale, Model model,CallendarDto cddto, int g_no, String year, String month)  {
		logger.info("calendallist 가자 {}.", locale);
		
		
		if(year==null) {
			
			Calendar calendar = Calendar.getInstance();
			
			 year = calendar.get(Calendar.YEAR)+"";
			 month =calendar.get(Calendar.MONTH)+1+"";
			
			cddto.setC_start(year.substring(2, 4)+util.isTwo(month));
			
			cddto.setG_no(g_no);
			
			model.addAttribute("cddto",cddto);
			
			
		}else {
			
			cddto.setC_start(year.substring(2, 4)+util.isTwo(month));

			cddto.setG_no(g_no);

			model.addAttribute("cddto",cddto);
			
		}
		


		List<CallendarDto> clist = callendarService.selectCallendarList(cddto);

		model.addAttribute("clist",clist);
		
	
		List<CallendarDto> cllist = callendarService.selectCallendarList_g_no(g_no);

		model.addAttribute("cllist",cllist);
		
		return "gallery/calendallist";

	}
	
	@RequestMapping(value = "/deleteCalendal.do", method = {RequestMethod.GET})
	public String deleteCalendal(Locale locale, Model model, int c_no, String g_no,String year,String month)  {
		logger.info("deleteCalendal 가자 {}.", locale);
		
		System.out.println(c_no);
		System.out.println("y"+year);
		System.out.println("m"+month);

		boolean isS = callendarService.deleteCallendar(c_no);

	
		if(isS) {
			return "redirect:selectGallery.do?"
					+ "g_return=two&g_no="+g_no+"&year="+year+"&month="+month;
		}else {
			logger.info("파일 업로드 실패");
			return "error";
		}

	}

	
	
	@RequestMapping(value = "/selectCallenderFrom.do", method = {RequestMethod.GET})
	public String selectCallenderFrom(Locale locale, Model model,int g_no, String c_start)  {
		logger.info("selectCallenderFrom 가자 {}.", locale);
		
		
		CallendarDto cudto = new CallendarDto();
		
		cudto.setG_no(g_no);
		cudto.setC_start(c_start);
		
		System.out.println("cd="+cudto);
		
		
		CallendarDto csdto = (CallendarDto) callendarService.selectCallendarList3(cudto);

		
		System.out.println("cs="+csdto);
		model.addAttribute("csdto",csdto);
		
		
		return "gallery/selectCallenderFrom";

	}

	/*
	@ResponseBody
	@RequestMapping(value = "/selectCallender.do", method = RequestMethod.POST)
	public String selectCallender(Locale locale, Model model, CallendarDto cudto

			)  {
		logger.info("selectCallender 가자 {}.", locale);
		
		
		
	
	
		String c_start= c_start_y.substring(2, 4)+util.isTwo(c_start_m)+util.isTwo(c_start_d);
		String c_end = c_end_y.substring(2, 4)+util.isTwo(c_end_m)+util.isTwo(c_end_d);
		
		callendarDto.setC_end(c_end);
		callendarDto.setC_start(c_start);
		callendarDto.setG_no(g_no);

 
		
		 callendarService.insertCallendar(callendarDto);
		 

		String kk = 
				
			//	"<script>opener.parent.location.reload();"
          //      + "window.close();</script>";
			//	year="+c_start_y+"&month="+c_start_m+"&g_no="+g_no
				
				"<script>opener.parent.location.href='selectGallery.do?year="+c_start_y+"&month="+c_start_m+"&g_no="+g_no+"&g_return=two"+ "';"
                + "window.close();</script>";
		
		return kk;

	}*/
	
	
	
	
	

	
	

	
	
	
	//랜덤한 값 32자리 만드는 메서드 
	public String createUUId() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/* 2019-05-02 ----------------------------------------------------------------------------------------------------- 
	 * 추가 : 조용권 
	 * 내용 : 메일 발송 관련 폼생성 메소드 , 메일 보내는 메소드*/
	
	//메일 발송 이벤트 
	public void SendMail(final String from , final String to , final String title ,final String msg) {
							//보내는메일주소			받는메일주소				제목				내용
		final MimeMessagePreparator preparator = new MimeMessagePreparator() { 
			@Override public void prepare(MimeMessage mimeMessage) throws Exception 
		{ final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
			String Message =SendMailForm(msg);
			helper.setFrom(from); 
			helper.setTo(to);
			helper.setSubject(title); 
			helper.setText(Message, true); }
			

		 }; 
		 mailSender.send(preparator); 
		 System.out.println("메일 발신 성공");

	}
	//메일 발송폼 
	public String SendMailForm(String msg) {
		String sendMailForm="<link href=\"https://fonts.googleapis.com/css?family=Jua\" rel=\"stylesheet\"> " + 
				" <link href=\"https://fonts.googleapis.com/css?family=Nanum+Brush+Script\" rel=\"stylesheet\"> " + 
				 "<div style=\"text-align: center; font-family: 'Jua', sans-serif; margin-top: 10%;\">\n" + 
				"    <h2>안녕하세요 <span style=\"font-family: 'Nanum Brush Script', cursive;\">[ 방구석갤러리 ]</span>입니다</h2> " + 
				"    <div style=\"text-align: center; font-family: 'Jua', sans-serif;\"> " + 				
				"        <p>"+msg+"</p> " + 
				"        <a href=\"http:// 192.168.3.106:8888/gallery/\" style=\"text-decoration: none; color: plum;\">확인하기</a> " + 
				"    </div> " + 
				" </div> ";
		
		
		return sendMailForm;
	}
	//-------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
}










