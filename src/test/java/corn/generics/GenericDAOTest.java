package corn.generics;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Álvaro Rungue
 * @since 11/01/15.
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
@ActiveProfiles(profiles = "test")
@SuppressWarnings("unchecked")
public abstract class GenericDAOTest<T extends Serializable> {


	@Autowired
	private GenericFileDAO<T> genericDAO;

	private Class<T> persistentClass;

	@Before
	public void initTest() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Test
	public void createTest(){
		String id = null;
		try {
			T t = this.getObjectToTest();
			this.genericDAO.save(t);
			Field field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			id = (String) field.get(t);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		assertNotNull(this.persistentClass.getSimpleName() + ".id não pode ser nulo.",id);
	}//fim createTest

	@Test
	public void deleteTest(){
		try {
			//Recuperar o objeto definido na classe concreta
			T t = this.getObjectToTest();
			this.genericDAO.save(t);

			//Recuperar o campo id para verificar se foi definido ao persistir o objeto
			Field field = t.getClass().getDeclaredField("id");
			field.setAccessible(true);
			String id = (String) field.get(t);

			//Verificar se o id não é nulo
			assertNotNull(this.persistentClass.getSimpleName() + ".id não pode ser nulo.", id);

			//Deletar o objeto
			this.genericDAO.delete(id);

			//Buscar o objeto que foi deletado e verificar se ele é nulo
			t = this.genericDAO.byId(id);
			assertNull(this.persistentClass.getSimpleName() + " deletado, deve retornar valor nulo.", t);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}//fim createTest

	/**
	 * Função para definir o tipo do objeto que é persistido pela DAO. A função deve retornar uma nova instancia
	 * do objeto, para este possa ser utilizada nos testes de CRUD definidos na
	 * classe {@link corn.generics.GenericDAOTest}.
	 * @return T - Objeto recebido na declaração da classe.
	 */
	public abstract T getObjectToTest();

}//fim class GenericDAOTest
