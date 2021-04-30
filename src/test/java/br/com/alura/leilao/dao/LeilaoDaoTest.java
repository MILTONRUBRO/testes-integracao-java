package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.LeilaoBuilder;
import br.com.alura.leilao.util.UsuarioBuilder;

class LeilaoDaoTest {
	
	private LeilaoDao dao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();

	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaCadastrarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = criarLeilao(usuario);
		
		Leilao salvo = dao.buscarPorId(leilao.getId());
		assertNotNull(salvo);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = criarLeilao(usuario);
		
		Leilao leilao2 = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(new UsuarioBuilder()
						.comNome("Fulano")
						.comEmail("fulano@email.com")
						.comSenha("1234678")
						.criar())
				.criar();
		
		leilao.setNome("Smartphone");
		leilao.setValorInicial(new BigDecimal("1000"));
		
		leilao = dao.salvar(leilao);

		Leilao salvo = dao.buscarPorId(leilao.getId());
		
		assertEquals("Smartphone", salvo.getNome());
		assertEquals(new BigDecimal("1000"), salvo.getValorInicial());

	}
	
	
	public Usuario criarUsuario() {
		Usuario usuario = new  Usuario("arya", "arya@email.com", "12345678");
		em.persist(usuario);
		return usuario;

	}
	
	public Leilao criarLeilao(Usuario usuario) {
		Leilao leilao = new Leilao("Notebook", new BigDecimal("2000"), LocalDate.now(), usuario);
		leilao = dao.salvar(leilao);
		return leilao;
	}

}
