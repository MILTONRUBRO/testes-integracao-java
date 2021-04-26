package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {
	
	private UsuarioDao dao;

	@Test
	void testeBuscaUsuarioPeloNome() {
		EntityManager em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		Usuario usuario = new Usuario("arya", "arya@emil.com", "12345678");
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

		Usuario usuarioEncontrado = dao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuarioEncontrado);
	}

}
