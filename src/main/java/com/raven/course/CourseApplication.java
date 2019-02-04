package com.raven.course;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raven.course.domain.Categoria;
import com.raven.course.domain.Cidade;
import com.raven.course.domain.Cliente;
import com.raven.course.domain.Endereco;
import com.raven.course.domain.Estado;
import com.raven.course.domain.ItemPedido;
import com.raven.course.domain.Pagamento;
import com.raven.course.domain.PagamentoComBoleto;
import com.raven.course.domain.PagamentoComCartao;
import com.raven.course.domain.Pedido;
import com.raven.course.domain.Produto;
import com.raven.course.domain.enums.EstadoPagamento;
import com.raven.course.domain.enums.TipoCliente;
import com.raven.course.repositories.CategoriaRepository;
import com.raven.course.repositories.CidadeRepository;
import com.raven.course.repositories.ClienteRepository;
import com.raven.course.repositories.EnderecoRepository;
import com.raven.course.repositories.EstadoRepository;
import com.raven.course.repositories.ItemPedidoRepository;
import com.raven.course.repositories.PagamentoRepository;
import com.raven.course.repositories.PedidoRepository;
import com.raven.course.repositories.ProdutoRepository;

@SpringBootApplication
public class CourseApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "70099794489", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("998561075", "987849100"));
		
		Endereco endereco1 = new Endereco(null, "Rua teste", "155", "Casa", "da pesada", "58073230", cliente1, c1);
		Endereco endereco2 = new Endereco(null, "Rua teste2", "1550", "Casa", "da pesada", "58073230", cliente1, c2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.save(cliente1);
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido ip1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(ip1, ip2));
		pedido2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
		
	}
}
