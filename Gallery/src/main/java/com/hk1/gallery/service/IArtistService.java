package com.hk1.gallery.service;

import java.util.List;

import com.hk1.gallery.dto.ArtistDto;

public interface IArtistService {
   boolean insertArtist(ArtistDto artistDto);       //U , M
   boolean updateArtist(ArtistDto artistDto);       //U , M
   boolean deleteArtist(int a_no);          //M
   ArtistDto    selectArtist(int a_no);         //U , M
   List<ArtistDto> selectArtistList(int a_state);      //U , M
   List<ArtistDto> selectArtistList();         //U , M
   List<ArtistDto> selectArtistListImg();
   
    public ArtistDto selectMemberId(int a_no);
    
   //작가등급 update
         boolean updateArtistState(ArtistDto artistDto);
}