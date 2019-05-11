package com.hk1.gallery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk1.gallery.dto.KyungmaeDto;
@Repository
public class KyungmaeDao implements IKyungmaeDao {
	
	private String namespace = "com.hk1.Kyungmae.";
	@Autowired
	private SqlSessionTemplate sqlSession;
	@Override
	public boolean insertKyungmae(KyungmaeDto kyungmaeDto) {							
		int count = sqlSession.insert(namespace+"insertKyungmae",kyungmaeDto);
		return count>0?true:false ;
	}

	@Override
	public boolean updateKyungmae(KyungmaeDto kyungmaeDto) {
		int count = sqlSession.update(namespace+"updateKyungmae",kyungmaeDto);
		return count>0?true:false ;
	}

	@Override
	public boolean deleteKyungmae(int k_no) {
		int count = sqlSession.delete(namespace+"deleteKyungmae",k_no);
		return count>0?true:false ;
	}


	@Override
	public KyungmaeDto selectK_noKyungmae(int k_no) {
		return sqlSession.selectOne(namespace+"selectKyungmae1", k_no);
	}

	@Override
	public KyungmaeDto selectI_noKyungmae(int i_no) {
		return sqlSession.selectOne(namespace+"selectKyungmae2", i_no);
	}
	@Override
	public List<KyungmaeDto> selectKyungmaeList() {
		 return sqlSession.selectList(namespace+"selectKyungmaeList1");
	}


	@Override
	public List<KyungmaeDto> selectKyungmaeList(String k_regdateORk_state) {
		if(k_regdateORk_state.equals("진행중")||k_regdateORk_state.equals("종료")) {
		String k_state =k_regdateORk_state;
		return sqlSession.selectList(namespace+"selectKyungmaeList2",k_state);
		}
		else {
		String k_regdate =k_regdateORk_state;
			return sqlSession.selectList(namespace+"selectKyungmaeList3",k_regdate);
		}
	}


}
