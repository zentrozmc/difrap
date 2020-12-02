package cl.difrap.productos.tiendaark.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.difrap.biblioteca.Controlador;
import cl.difrap.productos.tiendaark.dao.ItemDao;
import cl.difrap.productos.tiendaark.dto.Item;
@RestController
@RequestMapping("/item")
public class ItemCtrl extends Controlador<ItemDao,Item>
{

}
