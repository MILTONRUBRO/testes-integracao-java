package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {
	
	private UsuarioDao dao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();

	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void testeBuscaUsuarioPeloNome() {
		Usuario usuario = criarUsuario();
		Usuario usuarioEncontrado = dao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuarioEncontrado);
	}
	
	@Test
	void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		criarUsuario();
		assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("daenerys"));
	}
	
	@Test
	void deveriaRemoverUmUsuario() {
		Usuario usuario = criarUsuario();
		dao.deletar(usuario);
		assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));

	}
	
	public Usuario criarUsuario() {
		Usuario usuario = new  Usuario("arya", "arya@email.com", "12345678");
		em.persist(usuario);
		return usuario;

	}

}
