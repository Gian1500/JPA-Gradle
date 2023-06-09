package com.example.Persistence;

import com.example.Persistence.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Persistence")
public class PersistenceApplication {

	@PersistenceContext
	private EntityManager em;

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(PersistenceApplication.class, args);
		PersistenceApplication app = context.getBean(PersistenceApplication.class);
		app.createCliente();
	}

	@Transactional
	public void createCliente() {
		Cliente cliente = new Cliente();
		Domicilio domicilio = new Domicilio("San Martin", 1222);

		cliente.setNombre("Gian");
		cliente.setApellido("Curci");
		cliente.setDni(40373088);
		cliente.setDomicilio(domicilio);
		domicilio.setCliente(cliente);

		Factura factura1 = new Factura();
		factura1.setNumero(12);
		factura1.setFecha("10/08/2023");
		factura1.setTotal(120);
		factura1.setCliente(cliente);

		Categoria perecederos = new Categoria("Perecederos");
		Categoria lacteos = new Categoria("Lacteos");
		Categoria limpieza = new Categoria("Limpieza");

		Articulo art1 = new Articulo(200, "Yogurt Sabor Frutilla", 20);
		Articulo art2 = new Articulo(200, "Detergente Magistral", 80);

		art1.getCategorias().add(perecederos);
		art1.getCategorias().add(lacteos);
		lacteos.getArticulos().add(art1);
		perecederos.getArticulos().add(art1);

		art2.getCategorias().add(limpieza);
		limpieza.getArticulos().add(art2);

		DetalleFactura det1 = new DetalleFactura();
		det1.setArticulo(art1);
		det1.setCantidad(2);
		det1.setSubtotal(40);
		art1.getDetalle().add(det1);
		factura1.getDetalleFacturas().add(det1);

		em.persist(factura1);

		em.close();

		System.out.println("Cliente guardado en la base de datos: " + cliente.getId());
	}

}
