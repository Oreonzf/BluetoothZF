BluetoothZF
BluetoothZF é um aplicativo Android nativo desenvolvido em Kotlin, que permite a descoberta e conexão de dispositivos Bluetooth. Ele utiliza RecyclerView para listar dispositivos detectados e implementa suporte a permissões e funcionalidades modernas, compatíveis com Android 12 (API 31) e superiores.

📋 Funcionalidades
Habilitar/desabilitar Bluetooth.
Descobrir dispositivos Bluetooth próximos.
Conectar a dispositivos Bluetooth.
Exibir dispositivos descobertos em uma lista interativa (RecyclerView).
Gerenciar permissões de execução necessárias no Android 12+.
🚀 Tecnologias Utilizadas
Kotlin: Linguagem de programação.
Jetpack Compose: Construção de UI reativa.
RecyclerView: Exibição de listas de dispositivos descobertos.
Material Design 3: UI moderna e responsiva.
BluetoothAdapter: API do Android para manipulação de Bluetooth.
📱 Pré-requisitos
Android Studio Bumblebee ou superior.
Gradle configurado no mínimo na versão 7.0.
Android 5.0 (API 21) ou superior.
Permissões necessárias:
BLUETOOTH_CONNECT
BLUETOOTH_SCAN
ACCESS_FINE_LOCATION (apenas para descoberta em dispositivos mais antigos).
⚙️ Configuração e Execução
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/seu-usuario/bluetoothzf.git
cd bluetoothzf
Abra o projeto no Android Studio:

File > Open > Selecione o diretório do projeto.
Verifique as dependências no build.gradle:

As dependências principais incluem:

gradle
Copiar
Editar
implementation 'androidx.recyclerview:recyclerview:1.4.0'
implementation 'androidx.activity:activity-compose:1.7.2'
implementation 'androidx.compose.material3:material3:1.1.0'
Conceda permissões no dispositivo: Certifique-se de que as permissões de Bluetooth estão habilitadas no dispositivo em Configurações > Aplicativos.

Execute o aplicativo:

Conecte seu dispositivo ou emule no AVD.
Clique em Run no Android Studio.
📂 Estrutura do Projeto
bash
Copiar
Editar
app/
├── manifests/
│   ├── AndroidManifest.xml         # Permissões e configurações de atividades
├── java/com/example/bluetoothzf/
│   ├── bluetooth/
│   │   ├── BluetoothManager.kt     # Gerencia descoberta e eventos Bluetooth
│   │   ├── DeviceConnector.kt      # Gerencia conexões Bluetooth
│   ├── ui/
│   │   ├── views/
│   │   │   ├── MainActivity.kt     # Activity principal
│   │   ├── adapters/
│   │   │   ├── DeviceListAdapter.kt # Adapter para RecyclerView
│   │   ├── theme/
│   │   │   ├── Theme.kt            # Configurações de tema Material Design
│   ├── res/
│       ├── layout/
│       │   ├── activity_main.xml   # Layout inicial da aplicação
│       │   ├── device_list_item.xml # Layout para cada item na RecyclerView
🛠️ Como Funciona
Permissões
O aplicativo verifica e solicita permissões de BLUETOOTH_SCAN e BLUETOOTH_CONNECT em tempo de execução, garantindo compatibilidade com Android 12+.
Descoberta de Dispositivos
A classe BluetoothManager utiliza um BroadcastReceiver para escutar eventos de descoberta de dispositivos (BluetoothDevice.ACTION_FOUND) e atualiza a lista em tempo real.
Conexão
A classe DeviceConnector gerencia conexões com dispositivos Bluetooth usando BluetoothSocket.
🐞 Problemas Conhecidos
Certifique-se de que as permissões de localização estão habilitadas para dispositivos com Android 10 ou superior.
Testar o aplicativo em emuladores pode ter limitações no suporte a Bluetooth.
🤝 Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir um pull request ou issue.

📄 Licença
Este projeto é licenciado sob a MIT License.
