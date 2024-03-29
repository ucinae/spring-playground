package hansol.springelasticsearch.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories
public class ElasticConfig {

    @Value("${elasticsearch.host}")
    private String elasticHost;

    @Value("${elasticsearch.port}")
    private int elasticPort;

    @Value("${elasticsearch.clustername}")
    private String elasticClusterName;

    @Bean
    public Client client() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", elasticClusterName).build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(elasticHost), elasticPort));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
