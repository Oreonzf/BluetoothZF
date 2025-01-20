# **BluetoothZF**

BluetoothZF é um aplicativo Android nativo desenvolvido em **Kotlin**, que permite a descoberta e conexão de dispositivos Bluetooth. Ele utiliza **RecyclerView** para exibir dispositivos detectados e implementa suporte a permissões modernas, garantindo compatibilidade com **Android 12+ (API 31)**.

---

## **📋 Funcionalidades**

- Ativar/desativar Bluetooth no dispositivo.
- Descobrir dispositivos Bluetooth próximos.
- Conectar a dispositivos Bluetooth.
- Exibir dispositivos detectados em uma lista interativa (RecyclerView).
- Gerenciar permissões necessárias para Bluetooth em tempo de execução.

---

## **🚀 Tecnologias Utilizadas**

- **Kotlin**: Linguagem de programação principal.
- **Jetpack Compose**: Framework para UI reativa.
- **RecyclerView**: Exibição de listas de dispositivos.
- **Material Design 3**: Interface moderna e responsiva.
- **BluetoothAdapter**: API do Android para manipulação de Bluetooth.

---

## **📱 Pré-requisitos**

- **Android Studio Bumblebee** ou superior.
- **Gradle 7.0** ou superior.
- **Android 5.0 (API 21)** ou superior.
- Permissões necessárias:
  - `BLUETOOTH_CONNECT`
  - `BLUETOOTH_SCAN`
  - `ACCESS_FINE_LOCATION` (para dispositivos antigos, antes do Android 12).

---

## **⚙️ Configuração e Execução**

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/bluetoothzf.git
   cd bluetoothzf
   ```

2. **Abra o projeto no Android Studio:**
   - Vá em `File > Open` e selecione o diretório do projeto.

3. **Configure as dependências no `build.gradle`:**
   Certifique-se de que as dependências principais estão presentes:
   ```gradle
   implementation 'androidx.recyclerview:recyclerview:1.4.0'
   implementation 'androidx.activity:activity-compose:1.7.2'
   implementation 'androidx.compose.material3:material3:1.1.0'
   ```

4. **Conceda permissões no dispositivo:**
   - Certifique-se de que as permissões de Bluetooth estão habilitadas em **Configurações > Aplicativos**.

5. **Execute o aplicativo:**
   - Conecte um dispositivo físico ou emule no AVD.
   - Clique em **Run** no Android Studio.

---

## **📂 Estrutura do Projeto**

```
app/
├── manifests/
│   ├── AndroidManifest.xml         # Declaração de permissões e configurações da aplicação
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
│   │   │   ├── Theme.kt            # Configurações do tema Material Design
│   ├── res/
│       ├── layout/
│       │   ├── activity_main.xml   # Layout inicial
│       │   ├── device_list_item.xml # Layout para exibição de dispositivos
```

---

## **🛠️ Como Funciona**

### **Permissões**
O aplicativo solicita permissões como `BLUETOOTH_SCAN` e `BLUETOOTH_CONNECT` em tempo de execução, garantindo a compatibilidade com dispositivos Android 12+.

### **Descoberta de Dispositivos**
A classe `BluetoothManager` utiliza um `BroadcastReceiver` para escutar eventos de descoberta de dispositivos (`BluetoothDevice.ACTION_FOUND`) e atualiza a lista dinamicamente.

### **Conexão**
A classe `DeviceConnector` gerencia conexões Bluetooth utilizando `BluetoothSocket`. Após a conexão, ela permite comunicação por meio de streams de entrada e saída.

---

## **🐞 Problemas Conhecidos**

- Certifique-se de que as permissões de **localização** estão habilitadas em dispositivos com Android 10 ou superior.
- Emuladores Android podem apresentar limitações ao simular funcionalidades de Bluetooth.

---

## **🤝 Contribuições**

Contribuições são bem-vindas! Caso tenha sugestões, abra um **pull request** ou uma **issue**.

---

## **📄 Licença**

Este projeto é licenciado sob a [MIT License](LICENSE).

---

Pronto! Este README está organizado e legível para ser usado no GitHub. 😊
