package cl.difrap.apirest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.apirest.dao.AlbumDao;
import cl.difrap.apirest.dto.Album;
import cl.difrap.biblioteca.Controlador;

@RestController
@RequestMapping("/albumes")
public class AlbumCtrl extends Controlador<AlbumDao,Album>
{

}
