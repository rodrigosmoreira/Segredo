package com.br.usuariosApi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.br.usuariosApi.controller.dto.DetalhesUsuarioDtoTest;
import com.br.usuariosApi.controller.dto.EnderecoDto;
import com.br.usuariosApi.controller.helper.UsuarioControllerTestHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {
	
	private static final String MENSAGEM_VALIDACAO_NULO = "não pode ser nulo";
	private static final String MENSAGEM_CPF_INVALIDO = "CPF inválido";
	private static final String MENSAGEM_NÃO_DEVE_ESTAR_VAZIO = "não pode estar vazio";
	private static final String MENSAGEM_CAMPO_JÁ_ESTÁ_CADASTRADO = "Campo já está cadastrado";
	private static final String MENSAGEM_NAO_FOI_ENCONTRADO_USUARIO_COM_EMAIL = "não foi encontrando um usuario com o email informado";
	private static final String MENSAGEM_CEP_INVALIDO = "CEP está com formato invalido";
	
	private static final String ROTA_USUARIO = "/usuario";
	private static final String ROTA_ENDERECO = "/usuario/endereco";
	private static final String ROTA_ENDERECO_VIACEP = "/usuario/enderecoViaCep";
	
	@Autowired
	private MockMvc mockMvc;
	
	private UsuarioControllerTestHelper helper = new UsuarioControllerTestHelper();

	@Test
	public void deveriaDevolver201CasoDadosDeCadastroEstejamCorreto() throws Exception {
		String json = helper.retornaJsonUsuario("Diego Roberto Tiago da Paz", "diegorobertotiagodapaz__diegorobertotiagodapaz@vetech.vet.br", "168.454.455-67", "22/06/1945");
		
		testaCadastro(ROTA_USUARIO, 
				json,
				json, 
				HttpStatus.CREATED);
	}
	
	@Test
	public void deveriaDevolverStatus400ENomeObrigatorioCasoNomeEstejaVazio() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("", "heloiseritacarolinadasilva-85@clcimoveis.com.br", "815.049.461-89", "12/09/1941"),
						helper.retornaJsonCampoErro("nome", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ENomeObrigatorioCasoNomeEstejaNulo() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario(null, "heloiseritacarolinadasilva-85@clcimoveis.com.br", "815.049.461-89", "12/09/1941"),
						helper.retornaJsonCampoErro("nome", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarVazio() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Ricardo Miguel Galvão", "", "515.986.702-39", "16/11/1970"),
						helper.retornaJsonCampoErro("email", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarNulo() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Ricardo Miguel Galvão Nulo", null, "515.986.702-39", "16/11/1970"),
						helper.retornaJsonCampoErro("email", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailJaCadastrado() throws Exception {
		testaCadastroDuplicado(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Rosângela Mariana Araújo", "ricardohenrypires-70@pierproj.com.br", "749.614.866-04", "16/05/1942"),
						helper.retornaJsonUsuario("Rosângela Mariana Araújo", "ricardohenrypires-70@pierproj.com.br", "200.008.205-04", "16/05/1942"),
						helper.retornaJsonCampoErro("email", MENSAGEM_CAMPO_JÁ_ESTÁ_CADASTRADO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCpfNaoPodeEstarVazio() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Maya Daniela Heloisa Campos", "mayadanielaheloisacampos-76@associate.com.br", "", "22/06/1996"),
						helper.retornaJsonCampoErro("cpf", MENSAGEM_CPF_INVALIDO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCpfNaoPodeEstarNulo() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Maya Daniela Heloisa Campos", "mayadanielaheloisacampos-76@associate.com.br", null, "22/06/1996"),
						helper.retornaJsonCampoErro("cpf", MENSAGEM_VALIDACAO_NULO),
						HttpStatus.BAD_REQUEST);
	}
	
	
	@Test
	public void deveriaDevolverStatus400ECampoCpfJaCadastrado() throws Exception {
		testaCadastroDuplicado(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Rosângela Mariana Araújo", "teste@email.com", "749.614.866-04", "16/05/1942"),
						helper.retornaJsonUsuario("Eliane Cristiane Andrea Oliveira", "teste1@email.com", "749.614.866-04", "14/07/1985"),
						helper.retornaJsonCampoErro("cpf", MENSAGEM_CAMPO_JÁ_ESTÁ_CADASTRADO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoDataNascimentoNaoPodeEstarVazio() throws Exception {
		testaCadastro(ROTA_USUARIO, 
						helper.retornaJsonUsuario("Danilo Enrico Drumond", "daniloenricodrumonddaniloenricodrumond@deere.com", "982.367.691-78", ""),
						helper.retornaJsonCampoErro("dataNascimento", MENSAGEM_VALIDACAO_NULO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus201CasoOsDadosEstejamCorretoEForCadastroManual() throws Exception {
		insereCadastro(ROTA_USUARIO, helper.retornaJsonUsuario("Teste Martinez", "1234ttomasdavibrunodaluz@oralcamp.com.br" , "147.573.707-68", "13/06/1949"));
		
		String jsonEndereco = helper.retornaJsonEndereco("25212-650", "log", "121", "Bairro de teste", "Cidade de teste", "SP", "1234ttomasdavibrunodaluz@oralcamp.com.br");
		
		testaCadastro(ROTA_ENDERECO,
						jsonEndereco,
						jsonEndereco,
						HttpStatus.CREATED);
	}
	
	@Test
	public void deveriaDevolverStatus404CasoOsDadosEstejamCorretoENãoTiverUsuarioComOEmailEForCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO,
						helper.retornaJsonEndereco("25212-650", "log", "121", "Bairro de teste", "Cidade de teste", "SP", "testettomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NAO_FOI_ENCONTRADO_USUARIO_COM_EMAIL),
						HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCepNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("", "log", "121", "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cep", MENSAGEM_CEP_INVALIDO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCepNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco(null, "log", "121", "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cep", MENSAGEM_VALIDACAO_NULO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoLogradouroNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "", "121", "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("logradouro", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoLogradouroNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", null, "121", "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("logradouro", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoNumeroNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "", "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("numero", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoNumeroNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", null, "Bairro de teste", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("numero", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoBairroNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "21312", "", "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("bairro", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoBairroNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "123", null, "Cidade de teste", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("bairro", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCidadeNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "21312", "Bairro", "", "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cidade", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCidadeNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "123", "Bairro", null, "SP", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cidade", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEstadoNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "21312", "Bairro", "Cidade", "", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("estado", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEstadoNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "123", "Bairro", "Cidade", null, "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("estado", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarVazioCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "21312", "Bairro", "Cidade", "Estado", ""),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarNuloCasoSejaCadastroManual() throws Exception {
		testaCadastro(ROTA_ENDERECO, 
						helper.retornaJsonEndereco("11431-123", "logradouro", "123", "Bairro", "Cidade", "Estado", null),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus201CasoOsDadosEstejamCorretoEForCadastroViaCep() throws Exception {
		insereCadastro(ROTA_USUARIO, helper.retornaJsonUsuario("Teste Martinez", "123ttomasdavibrunodaluz@oralcamp.com.br" , "109.111.698-90", "13/06/1949"));
		
		
		testaCadastro(ROTA_ENDERECO_VIACEP,
						helper.retornaJsonEnderecoViaCep("25212-650", "123ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonEndereco("25212-650", "Rua Leitão da Cunha", null, "Parque Uruguaiana", null, null, "123ttomasdavibrunodaluz@oralcamp.com.br"),
						HttpStatus.CREATED);
	}
	
	@Test
	public void deveriaDevolverStatus404CasoOsDadosEstejamCorretoENãoTiverUsuarioComOEmailEForCadastroViaCep() throws Exception {
		testaCadastro(ROTA_ENDERECO_VIACEP,
						helper.retornaJsonEnderecoViaCep("25212-650", "testettomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NAO_FOI_ENCONTRADO_USUARIO_COM_EMAIL),
						HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCepNaoPodeEstarVazioCasoSejaCadastroViaCep() throws Exception {
		testaCadastro(ROTA_ENDERECO_VIACEP, 
						helper.retornaJsonEnderecoViaCep("", "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cep", MENSAGEM_CEP_INVALIDO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoCepNaoPodeEstarNuloCasoSejaCadastroViaCep() throws Exception {
		testaCadastro(ROTA_ENDERECO_VIACEP, 
						helper.retornaJsonEnderecoViaCep(null, "ttomasdavibrunodaluz@oralcamp.com.br"),
						helper.retornaJsonCampoErro("cep", MENSAGEM_VALIDACAO_NULO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarVazioCasoSejaCadastroViaCep() throws Exception {
		testaCadastro(ROTA_ENDERECO_VIACEP, 
						helper.retornaJsonEnderecoViaCep("11431-123", ""),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus400ECampoEmailNaoPodeEstarNuloCasoSejaCadastroViaCep() throws Exception {
		testaCadastro(ROTA_ENDERECO_VIACEP, 
						helper.retornaJsonEnderecoViaCep("11431-123", null),
						helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NÃO_DEVE_ESTAR_VAZIO),
						HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void deveriaDevolverStatus200SeUsuarioEstaCadatradoNoSistemaQuandoConsultarDetalhesUsuario() throws Exception {
		insereCadastro(ROTA_USUARIO, helper.retornaJsonUsuario("Nome", "teste1@acesso.com", "104.831.601-72", "22/12/2010"));
		
		insereCadastro(ROTA_ENDERECO_VIACEP, helper.retornaJsonEnderecoViaCep("25212-650", "teste1@acesso.com"));
		
		EnderecoDto enderecoDto = new EnderecoDto("25212-650", "Rua Leitão da Cunha", null, "Parque Uruguaiana", null, null, "");
		
		DetalhesUsuarioDtoTest detalhesUsuarioDto = new DetalhesUsuarioDtoTest("Nome", "teste1@acesso.com", "104.831.601-72", "22/12/2010", new ArrayList<>(Arrays.asList(enderecoDto)));
		
		testaAcesso("/usuario/teste1@acesso.com", 
					helper.retornaJson(detalhesUsuarioDto),
					HttpStatus.OK);
	}

	@Test
	public void deveriaDevolverStatus404EUsuarioNaoEstaCadatradoNoSistemaQuandoConsultarDetalhesUsuario() throws Exception {
		testaAcesso("/usuario/teste@gmail.com", 
					helper.retornaJsonCampoErro("emailUsuario", MENSAGEM_NAO_FOI_ENCONTRADO_USUARIO_COM_EMAIL),
					HttpStatus.NOT_FOUND);
	}
	
	private void testaCadastroDuplicado(String uri, String jsonDuplicado, String jsonEntrada, String jsonSaida, HttpStatus httpStatus) throws URISyntaxException, Exception {
		
		insereCadastro(uri, jsonDuplicado);
		
		testaCadastro(uri, jsonEntrada, jsonSaida, httpStatus);
	}
	
	public void insereCadastro(String uri, String jsonEntrada) throws Exception, URISyntaxException {
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(new URI(uri))
					.content(jsonEntrada)
					.contentType(MediaType.APPLICATION_JSON));
	}
	
	public void testaCadastro(String uri, String jsonEntrada, String jsonSaida, HttpStatus httpStatus) throws Exception, URISyntaxException {
		
		mockMvc
			.perform(MockMvcRequestBuilders
					.post(new URI(uri))
					.content(jsonEntrada)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(httpStatus.value()))
			.andExpect(MockMvcResultMatchers
						.content()
						.json(jsonSaida));
	}
	
	public void testaAcesso(String uri, String jsonSaida, HttpStatus httpStatus) throws Exception, URISyntaxException {
		mockMvc
			.perform(MockMvcRequestBuilders
					.get(new URI(uri))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers
					.status()
					.is(httpStatus.value()))
			.andExpect(MockMvcResultMatchers
						.content()
						.json(jsonSaida));
	}
	
}