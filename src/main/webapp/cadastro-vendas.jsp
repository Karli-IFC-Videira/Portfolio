<%-- 
    Document   : cadastro-vendas
    Created on : 4 de dez. de 2024, 17:58:27
    Author     : Jesus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page = "header.jsp" />
<h1>Área de vendas</h1>

    <section class="container-vendas">
        <!-- Dados da Venda -->
        <div class="dados-venda">
            <h2>Dados da Venda</h2>

            <!-- Código do produto -->
            <label class="codProd" for="codigoProduto" aria-label="Código do produto">Código do produto</label>
            <input type="number" id="codigoProduto" placeholder="Insira aqui o código do produto"
                aria-label="Código do produto">

            <!-- Quantidade -->
            <label class="codProd" for="quantidade" aria-label="Quantidade de produto">Quantidade</label>
            <input type="number" id="quantidade" min="1" placeholder="Quantidade" aria-label="Quantidade de produto">

            <!-- Botão de adicionar produto -->
            <button id="adicionarProduto" aria-label="Adicionar produto à venda">Adicionar Produto</button>
        </div>

        <!-- Tabela de Produtos -->
        <div class="dados-tabela">
            <h2>Detalhes da Venda</h2>

            <!-- Código da venda -->
            <label class="venda" for="codigoVenda" aria-label="Código da venda"></label>
            <input type="text" id="codigoVenda" placeholder="Código da venda" readonly aria-label="Código da venda">

            <table>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Descrição</th>
                        <th>Preço Unitário</th>
                        <th>Quantidade</th>
                        <th>Subtotal</th>
                        <th>Opções</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Linhas serão adicionadas dinamicamente -->
                </tbody>
            </table>

            <div class="botoes">
                <!-- Botão de cadastrar venda -->
                <button id="cadastrarVenda" class="btn-verde" aria-label="Cadastrar venda">Cadastrar Venda</button>

                <!-- Botão de cancelar venda -->
                <button id="cancelar" class="btn-vermelho" aria-label="Cancelar venda">Cancelar</button>
            </div>

            <!-- Total da venda -->
            <label class="valVenda" for="totalVenda" aria-label="Total da venda">Total Venda:</label>
            <input type="text" id="totalVenda" readonly aria-label="Total da venda" />
        </div>
    </section>
