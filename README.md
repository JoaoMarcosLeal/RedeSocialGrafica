# Rede Social Gráfica

Este projeto é usado para exercitar os conceitos de *GUI* (Interfaces Gráficas de Usuário) e Tratamento de Exceção.

A implementação usa como base uma versão traduzida do exemplo do livro *Programacao Orientada a Objetos com Java - uma introdução prática utilizando BlueJ*, de **Barnes e Kolling**.

## Sobre o Projeto

Este projeto é uma evolução do exemplo anterior, `RedeSocial`, que agora incluir uma interface gráfica e que
permite postar inclusive mensagens com fotos de verdade.

Em relação ao projeto usado anteriormente nas aulas de Polimorfismo este projeto acrescenta:

- Um atributo ID (identificador) para cada mensagem.
- Alteração na classe `MensagemFoto` para tratar imagens.
- A implementação das operações de curtir e comentar na classe `FeedNoticias`.
- Uma interface chamada `Publicacao` que é implementada pela classe `Mensagem`.
- Uma classe chamada `TelaRedeSocial` que implementa a interface gráfica para a Rede Social.

## Exercícios - Parte 1 - Interfaces Gráficas

Faça os passos abaixo.
Não esqueça de:

- Testar sua implementação a cada passo.
- Fazer `commit` e sincronizar as alterações após cada passo terminado.

### Passo 1.0

Execute o programa e veja como ele funciona (o que faz e o que ainda não faz).

Em seguida avalie o código do programa.

Veja que a interface `Publicacao` foi criada para que a classe `TelaRedeSocial` tivesse acesso às publicações, mas sem poder alterá-las (respeitando o encapsulamento e diminuindo o acoplamento).
Inclusive as classes que representam mensagens não são públicas pois não precisam
(e nem devem) estar acessíveis de fora do pacote `feed`.

### Passo 1.1

Você deve ter notado que o botão `Comentar` não está fazendo nada. 
Faça as implementações necessárias para que ele passe a funcionar (obs.: não é necessário tratar o autor).

> Dica: veja o tratamento do botão `Curtir` como exemplo.

### Passo 1.2

Acrescente na tela um botão `Atualizar` que recarregue o Feed de Notícias.

Obs.: se você colocar um texto muito grande no botão pode ser que ele não apareça por não caber na tela.

### Passo 1.3

O Feed de Notícias da Rede Social tem tamanho fixo.
Se as mensagens não couberem na tela, elas simplesmente não aparecem.
Experimente, por exemplo, postar muitas mensagens até não caberem na tela (você pode postar apenas duas mensagens, por exemplo, e diminuir o tamanho da tela).

Vamos melhorar isso colocando o componente que mostra o feed de notícias dentro de um `JScrollPane`.

Um `JScrollPane` é um objeto que permite acrescentar a funcionalidade de barra de rolagem ao componente `JTextArea`.

- Ele acaba funcionando como uma camada intermediária entre o `JPanel` e o `JTextArea`.
- Portanto, você deve criar um `JScrollPane` passando o componente do feed (`JTextArea`) como parâmetro do construtor.
- E então adicionar o `JScrollPane` na janela (ao invés do componente do feed).

Teste a sua implementação, verificando se a barra de rolagem apareceu quando necessário.

### Passo 1.4

Os componentes da biblioteca Swing que estamos utilizando para construir as interfaces gráficas têm um visual com cara de software "antigo".
Podemos melhorar isso utilizando a [biblioteca FlatLaf](https://www.formdev.com/flatlaf/).
Com ela, podemos deixar nossos programas com um visual mais moderno e atraente, acrescentando apenas duas linhas de código.

Vamos então usar a biblioteca FlatLaf para mudar o visual da Rede Social Gráfica.
Para isso, siga os passos abaixo:

1. Adicione a biblioteca (arquivo de extensão `.jar`) na pasta lib do projeto - Obs.: isso já foi feito para você ;)
2. Importe a classe `FlatDarkLaf`, acrescentando a linha `import com.formdev.flatlaf.FlatDarkLaf;` ao arquivo `TelaRedeSocial.java`.
3. Acrescente a chamada `FlatDarkLaf.setup();` logo após a criação da janela no método `construirJanela` da classe `TelaRedeSocial`.
   Obs.: precisa ser antes de criar os componentes da janela.
4. Execute seu programa e veja o que mudou. O que achou do novo visual?

### Passo 1.5

Que tal mudarmos a fonte do nosso Feed de Notícias?

A classe `JTextArea` tem um método chamado `setFont` que muda a fonte utilizada.
Ele espera um objeto da classe `Font` que pode ser criado passando-se o nome da fonte, um estilo e o tamanho da fonte (ex: `new Font("Serif", Font.ITALIC, 16)`).
Fique à vontade para escolher a fonte e o estilo que prefere utilizar.

> Dica 1: nem sempre o VS Code consegue identificar automaticamente o pacote onde se encontra a classe `Font`.
> Se tiver esse problema, lembre-se que basta importar a classe manualmente usando `import java.awt.Font;`.

> Dica 2: A fonte utilizada precisa estar instalada em seu Sistema Operacional. Se usar uma fonte não instalada, o programa usará alguma padrão.

### Passo 1.6

Nossa Rede Social mostra no Feed de Notícias as mensagens de todos os autores. 
Mas suponha que queiramos uma forma mais simples de encontrar as mensagens de um determinado autor.
Podemos, por exemplo, criar uma caixa de seleção para selecionar um autor, e o Feed de Notícias ser filtrado para mostrar apenas as mensagens daquele autor.
Para isso, faça o seguinte:

1. Altere a classe `FeedNoticias` para que ela tenha uma lista com os autores das mensagens.
   Uma das formas de fazer isso é: toda vez que uma nova mensagem for adicionada, se o autor ainda não está na lista, ele é incluído.
   Crie também um método para retornar a lista com todos os autores (cuidado com o encapsulamento!).
   Faça as alterações necessárias e teste o programa.

2. Na classe `FeedNoticias`, crie uma sobrecarga do método `getPublicacoes` que recebe um autor por parâmetro e retorna apenas as mensagens dele.
   Teste sua implementação.

3. Vamos agora acrescentar à nossa tela uma caixa para o usuário selecionar o autor das mensagens que ele quer ver.
   Uma caixa de seleção é um objeto da classe `JComboBox` que possui uma lista de objetos (e cada objeto se torna uma opção na caixa de seleção).
   Vamos então:

    - Criar a caixa de seleção (obs.: um `JComboBox<T>` depende de um segundo tipo `T`, assim como um `ArrayList`).
    - Colocar a caixa de seleção dentro de um painel que esteja na área superior (`NORTH`) do `BorderLayout`.
    - Criar um método que recarrega a caixa de seleção com os autores. 
      Para isso, use os métodos `removeAllItems` e `addItem` da classe `JComboBox`.
      Você deve sempre adicionar primeiro a string `"Todos"` e, depois, cada um dos autores retornados pelo feed de notícias.
      O método criado deve ser chamado logo após a criação da caixa de seleção, e também toda vez que uma nova mensagem for postada.
    - Alterar o método `atualizarAreaTextoFeed` para que ele carregue o feed de acordo com a escolha do usuário na caixa de seleção (dica: você pode obter a opção escolhida pelo usuário usando os métodos `getItemAt`e `getSelectedIndex` da classe `JComboBox`)
    - Tratar o evento de clique da caixa de seleção da mesma forma que fazemos para os botões e atualizar o feed de notícias de acordo com o autor escolhido. **Importante**: quando os itens da caixa de seleção são alterados, o evento de clique é gerado; então, para evitar erros, é necessário garantir que a atualização do feed não será feita enquanto os itens estão sendo recarregados (veja dica abaixo).
    - Seria interessante acrescentar também um rótulo para que o usuário saiba que a caixa de seleção é para escolher o autor.

> **Dica** sobre o possível erro ao recarregar a caixa de seleção: 
> 
> A questão é que no método que preenche a caixa de seleção, há uma chamada a um método que remove todos os itens da caixa.
> E, quando isso acontece, um evento de seleção da caixa pode ser gerado (e isso é tratado em outra thread). 
> Com isso, o restante do método que preenche a caixa de seleção, e o tratamento do evento (que chama o atualizar) acabam 
> sendo executados em paralelo. Esse paralelismo pode causar erros no método de atualização.
> 
> A ideia então é evitar que as duas coisas possam rodar em paralelo. 
> Para isso, podemos alterar o método que preenche a caixa de seleção, acrescentado um atributo booleano que informe se 
> os dados estão sendo carregados ou não. 
> Algo como:
> 
> ```java
> public void preencherCaixaAutores() {
>     carregandoCaixaAutores = true;
>     // ... código que você já tinha feito no método
>     carregandoCaixaAutores = false;
> }
> ```
> 
> Agora, no tratamento do evento da caixa de seleção, usamos o booleano `carregandoCaixaAutores` para só chamar 
> o método de atualização se a variável for `false` (ou seja, se a caixa não estiver sendo carregada).

Teste suas implementações.

### (Opcional) Passo 1.7

Vamos criar um menu com as opções: `Postar Texto`, `Postar Foto`, `Visualizar`, `Curtir`, `Comentar` e `Sair`.
Crie os menus e trate os eventos conforme necessário.

Dica: use os slides da aula de Interfaces Gráficas para ver exemplos de criação dos menus.

### (Opcional) Passo 1.8

Vamos agora fazer com que um menu *popup* apareça quando clicamos na área de texto do feed de notícias.
Para isso, você precisará tratar os eventos de mouse da área de texto (use o método `addMouseListener`).
No evento de clique do mouse você exibirá um menu *popup* criado conforme descrição abaixo.

Um menu *popup* (classe `JPopupMenu`) funciona como um menu normal, ou seja, ele é formado por objetos do tipo `JMenuItem`.
Nosso menu *popup* deverá ter os ítens de menu: `Atualizar` e `Limpar`. 
O primeiro recarrega o feed (como o botão do Passo 1.2), já o segundo deve limpar o feed de notícias.

Obs: a classe `JPopupMenu` possui um método `show` que espera um componente e a posição `x`, `y` onde o menu deve aparecer (obtenha essas informações do objeto `MouseEvent`, que vem por parâmetro no tratamento do clique do mouse).

## Exercícios - Parte 2 - Tratamentos de Exceção

Faça os passos abaixo.
Assim como na primeira parte, não se esqueça de:
- Testar sua implementação a cada passo.
- Fazer `commit` e sincronizar as alterações após cada passo terminado.

### Passo 2.1

O que acontece se o usuário clicar no botão `Curtir` e digitar um valor que não seja um número? Ou então se clicar em cancelar?

Veja que ocorre uma exceção (ele aparece no terminal do VS Code).
Na mensagem de erro que aparece você pode ver que a exceção que é ocorre é do tipo `NumberFormatException` e ela acontece
porque o método `parseInt` da classe `Integer` não consegue converter o texto digitado para um número.

Neste passo você deve então acrescentar um tratamento para a exceção do tipo `NumberFormatException` no método que trata a opção
de curtir as mensagens.
Caso o usuário digite algum texto que não seja um número, ou clique em cancelar, deve ser exibida uma mensagem informando que
o valor é inválido, e precisa ser numérico.

> Dica: você pode usar a classe `JOptionPane` para exibir uma mensagem como no exemplo abaixo:
> ```java
> JOptionPane.showMessageDialog(janela, "Uma mensagem de erro", "Um titulo", JOptionPane.ERROR_MESSAGE);
> ```

### Passo 2.2

O botão `Curtir` e o botão `Comentar` pedem para o usuário o identificador da mensagem.
Mas se o usuário informar um identificador de uma mensagem que não existe, ocorre um erro na aplicação (você pode ver a mensagem de erro no terminal do VS Code).

Neste passo e no passo seguinte vamos tratar essa questão, mas com estratégias diferentes para cada um e depois analisarmos as vantagens e desvantagens de cada estratégia.

Neste passo, altere o método que trata **as curtidas** na classe de Feed de Notícias para que ele retorne um booleano indicando se a mensagem foi realmente curtida ou não.
Portanto, quando a mensagem não existir, o método deve retornar `false`.

Em seguida, faça o tratamento da operação de curtir na classe `TelaRedeSocial`, usando o retorno booleano do método da classe de Feed de Notícias e informando o usuário caso o identificador digitado seja de uma mensagem que não existe.

### Passo 2.3:

Agora, vamos alterar o método que trata os comentários na classe de Feed de Notícias.
Mas, nesse caso, vamos alterar o método de forma que ele lance uma exceção caso o identificador da mensagem não exista (use exceção do tipo `RuntimeException`).

Teste sua aplicação sem ainda tratar a exceção.
Veja que a mensagem de erro que aparece é a que você usou ao criar o objeto da exceção.

Agora faça o tratamento da exceção na classe `TelaRedeSocial`.
Por enquanto, apenas exiba a mensagem tratada para o usuário.

### Passo 2.4

1. O que você achou das duas estratégias que adotamos para tratar a operação de curtir (retorno booleano) e de comentar (usando exceções)?
2. Quais lhe parecem ser as vantagens e desvantagens de usar cada uma das estratégias?

*... escreva aqui sua resposta ...*

### Passo 2.5

Em relação à exceção gerada no tratamento da operação de comentar, como podemos nos recuperar da exceção?
Poderíamos pedir para o usuário informar o identificador da mensagem novamente, certo?

Altere então o código de forma que o programa continue pedindo o identificador para o usuário até ele digitar um identificador válido.

### Passo 2.6:

Depois da alteração anterior, o que acontece com seu programa caso o usuário tente comentar uma mensagem antes de existir qualquer publicação?
Caso não tenha tratado esse caso, faça o tratamento adequado agora.

### Passo 2.7:

Do jeito que fizemos até agora, o programa apenas exibe a mesma mensagem de erro da exceção.
Mas pode ser que queiramos exibir uma mensagem mais amigável para o usuário.
A mensagem do lançamento da exceção é escrita para outros programadores e nem sempre faz sentido para um usuário do progrma.
Dessa forma, pode ser interessante capturar a exceção e usar os dados disponíveis nela para montar uma mensagem mais apropriada.

Crie então um classe `MensagemNaoEncontradaException` que herda da classe `NoSuchElementException` 
(escolhemos herdar dela pois ela representa melhor a situação do erro que estamos tratando).
A classe deverá ter como atributo o identificador utilizado.
Veja que o construtor pode receber apenas o identificador e não precisa, necessariamente, montar uma mensagem da exceção. 
Dessa forma, ao lançarmos uma exceção com essa classe, não precisamos nos preocupar a mensagem.

Faça com que seja lançada uma exceção da classe criada.
Altere a classe `TelaRedeSocial` para que capture uma exceção desse tipo específico (`MensagemNaoEncontradaException`) e 
defina sua própria mensagem para o usuário buscando o identificador da mensagem a partir do objeto da exceção.
(Obs.: nesse exemplo específico, não seria necessário obter o id da classe de exceção, pois provavelmente temos como pegar 
isso de uma variável no método onde ocorre o erro; mas é apenas uma maneira didática de exercitarmos o conceito).

### (Opcional) Passo 2.8:

Altere o tratamento da exceção na classe de `TelaRedeSocial` para que o tratamento funcione apenas caso seja uma exceção do tipo que lançamos.
Acrescente um tratamento genérico (`Exception`) que apenas mostra a mensagem de erro para os demais casos.

Execute o programa para confirmar que do ponto de vista do usuário nada mudou (teste com identificadores válidos e inválidos).

Apenas como forma de testar nossa última alteração, acrescente dentro do método `Curtir` da classe `Mensagem` uma divisão por zero qualquer.
Isso terá um efeito de provocar uma exceção que não é do tipo `MensagemNaoEncontradaException`. 
Dessa forma, o tratamento deverá cair no caso geral de apenas mostrar a mensagem de erro.

