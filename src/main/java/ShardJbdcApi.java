
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName Demo
 * @Description 简单的Sharding-JDBC 原生API使用
 *              官方地址得磨很详细：https://shardingsphere.apache.org/document/4.1.1/cn/manual/sharding-jdbc/usage/sharding/
 * @Author bellus
 * @Date 2022/6/6 18:56
 * @Version 1.0.0
 **/

public class ShardJbdcApi {
    private static int userId;

    public static void main(String[] args) throws SQLException {

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://10.0.193.128:3306/shard0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("shard0", dataSource1);

        // 配置第二个数据源
        BasicDataSource dataSource2 = new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://10.0.193.128:3306/shard1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("root");
        dataSourceMap.put("shard1", dataSource2);

        // 配置 Order 表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "shard${0..1}.t_order${0..1}");

        // 配置分库 + 分表策略
        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "shard${user_id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());


        String sql = "SELECT * from t_order WHERE user_id=? and order_id =? ";

        int orderId = 5;
        int userId = 2;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, orderId);

            System.out.println("查询参数：orderId =" + orderId + ", userId =  " + userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                System.out.println("返回结果Size="+ rs  );
                while (rs.next()) {
                    // %2 结果，路由到 shard1.order1
                    System.out.println("orderId---------" + rs.getInt(1) +  "       userId---------" + rs.getInt(2));

                }
            }
        }
    }
}
