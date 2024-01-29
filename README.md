# Não exponha a chave primária (GUID ou ID interno) em um URL

fonte: https://www.reddit.com/r/dotnet/comments/17gimju/do_not_expose_primary_key_guid_or_int_id_in_an_url  

1. **Antes, pensar na necessidade de aplicar autorização na aplicação.**   
Contanto que você tenha segurança adequada, isso não importa. Se for uma questão de aparência, você pode tentar as outras opções mencionadas ou fazer a codificação base64 do ID, como fiz no passado.

2. **Retorne o ID, somente quando necessário do necessário.**  
Mesmo com a segurança adequada, com IDs sequenciais ainda pode divulgar algumas informações não públicas sobre os seus dados. Por exemplo, se você puder adicionar itens regularmente, poderá monitorar o número total de itens adicionados verificando a diferença de ID entre suas postagens. Ou talvez você tenha um item ao qual tenha acesso, mas a data de criação não seja pública. Você poderá aproximar a hora de criação pelo mesmo método acima.

3. **IDs sequenciais podem indicar o tamanho do seu negócio.**   
Muitas pessoas estão perdendo o verdadeiro motivo que as leva a fazer isso. Não está relacionado à autenticação. Muitas empresas não querem expor IDs sequenciais porque isso vaza detalhes comerciais.  
IDs sequenciais podem indicar o tamanho do seu negócio ou a taxa de transações dentro dele e podem ser usados para adivinhar o crescimento/saúde geral da sua empresa. Por exemplo, com IDs sequenciais, se eu criar uma conta para um serviço todos os dias no mesmo horário durante uma semana, posso consultar o ID da minha conta para inferir o crescimento diário da conta e o número geral de contas.  
Embora isso possa parecer bobagem e vaidade, empresas de todos os tamanhos se preocupam com isso. As startups não querem que você saiba que seu cliente 2 porque querem que você confie neles. As empresas de capital aberto não querem que você saiba que elas têm X novas contas por dia porque isso fornece dados adicionais que podem ser usados em negociações (na verdade, acho que pode até fazer parte da conformidade com a SOX, mas não posso confirmar).  
Existem pelo menos maneiras de lidar com isso:  
use um guia real como chave primária ou secundária  
codifique seu ID (hashids, floco de neve, etc).  

4. **Como alternativa, podemos adotar indices inteiros ou longos, mas GUID para pesquisa, este quenão será indexado por questões de performance.**  
Gosto de usar ints ou longs como índice clusterizado (geralmente chave primária) e um GUID aleatório como minha chave de pesquisa (índice exclusivo). NUNCA faça um GUID aleatório como seu índice clusterizado, suas inserções morrerão com uma tabela grande, aleatoriamente.

5. **Parece ser uma solução simples, usar POST? Mas talvez o ID já pode ter vindo no retorno no LOCATION na criação do recurso.**   
Se você não quiser expor nenhum tipo de ID, basta usar POST. Sim, eu sei... Tranquilo e merda, mas se isso te preocupa, então não use GET para passar a string de consulta. Use Post e no corpo envie o modelo ou id.  

