# La IA escribe código, pero hace falta entender el repo

Ejemplos reales de [dashpay/rust-dashcore](https://github.com/dashpay/rust-dashcore) y
[dashpay/platform](https://github.com/dashpay/platform). Las marcas de IA ("Generated with Claude
Code", "Co-Authored-By: Claude", ramas `claude/` o `codex/`, cuentas de agentes) salen de las propias PRs y
commits.

> Yo también uso IA: muchas de mis PRs llevan "Co-Authored-By: Claude". El problema no es la
> herramienta, es usarla sin saber cómo es y cómo debería ser la estructura del proyecto.

## 1. PRs de compañeros que frené o reconduje

### rust-dashcore

- [#1100](https://github.com/dashpay/rust-dashcore/pull/1100) (Claude, cerrada): +217 líneas para
  excluir outpoints de la selección de monedas. Ya existía `require_final_inputs()` y además la
  política quedaba fuera de la wallet. El autor usó lo existente: *"much less code"*.
- [#1028](https://github.com/dashpay/rust-dashcore/pull/1028) (agente de IA, cerrada): nueva API de
  restauración y un campo serializado que rompía el formato bincode existente. Bastaba con la
  serialización que key-wallet ya tenía.
- [#1002](https://github.com/dashpay/rust-dashcore/pull/1002) (Claude, cerrada): hacía más robusto un
  re-escaneo hacia atrás. *"I don't think the backward re-scan is the right direction"*. Lo eliminé
  después en #1016.
- [#1008](https://github.com/dashpay/rust-dashcore/pull/1008) (Claude, cerrada): era un subconjunto
  de [#989](https://github.com/dashpay/rust-dashcore/pull/989), que ataca la causa raíz.
- [#979](https://github.com/dashpay/rust-dashcore/pull/979) (Claude, cerrada): con benchmark, 8 GB de
  RAM y 1204 s frente a la alternativa. El autor: *"Most of the changes were found not to be
  necessary"*.
- [#189](https://github.com/dashpay/rust-dashcore/pull/189): código ajeno a la PR y sin usar. Lo quité
  con un commit llamado *"this doesn't belong to this PR"*.
- [#1191](https://github.com/dashpay/rust-dashcore/pull/1191): El colega esta haciendo malabares cuando la logica ya existe.

### platform

- [#3842](https://github.com/dashpay/platform/pull/3842) (Claude): helpers BIP39 reimplementados dentro
  de la capa FFI. *"key-wallet already has logic related to mnemonic Bip39… I don't think we should
  re-implement it"*. *"FFI crates should have no logic other than converting C types to Rust, calling
  Rust functions, and converting Rust types to C."* Pedí cambios tres veces. La lógica se subió a
  key-wallet, se quitaron los wrappers y se borraron los tests duplicados.
- [#4038](https://github.com/dashpay/platform/pull/4038) (Claude, rama `claude/...`): parche en
  `build_ios.sh` para un síntoma que ya resolvía #3853, y cambios en tests que duplicaban #4037.
  *"This is not needed, tell AI to rebase and remove all modifications to the Swift Integration
  tests"*. *"the issue is in the build.rs"*. El autor quitó las dos cosas.
- [#3817](https://github.com/dashpay/platform/pull/3817) (Claude, cerrada): sweep de CoinJoin
  implementado en platform. *"this code should live in rust-dashcore, platform-wallet core.rs module is
  just an wrapper around it"*. Se intentó mover a rust-dashcore con un modo de `TransactionBuilder`.
- [#3981](https://github.com/dashpay/platform/pull/3981) (commits de Claude): un módulo intermedio de
  decodificación con tipos que nadie usaba, y helpers de test que ya existían en dashcore. *"I don't
  see a usage for DecodedTransaction/DecodedInput/DecodedOutput"*. Se borró el módulo.
- [#3548](https://github.com/dashpay/platform/pull/3548) (Claude y Codex, +9502): copiaba a escala una
  mala convención: símbolos escritos a mano con `@_silgen_name` y un sistema de errores *"kinda
  overkill"*. Quedó aplazado; lo arreglé yo en #3553 y #3566.
- [#3137](https://github.com/dashpay/platform/pull/3137) (agente de IA): tests que no probaban nada
  y tests que no pertenecen al SDK. Ante la rama base equivocada, el bot solo cambiaba la base de un
  lado a otro (*"dear lord, is not as easy as that"*). La sustituyó #3222.
- [#5307](https://github.com/dashpay/platform/pull/5307): reimplementaba `clear_storage()` y
  `is_running()` del cliente SPV en lugar de usarlos, y creaba un cliente nuevo en cada `start()`. Lo
  señalé, pero se fusionó sin cambios.

## 2. Mis PRs que arreglan o añaden quitando código

### rust-dashcore

- [#284](https://github.com/dashpay/rust-dashcore/pull/284) (+2/−331): validación de InstantSend
  duplicada que ya existía en otro crate. El original lleva "Generated with Claude Code".
- [#280](https://github.com/dashpay/rust-dashcore/pull/280) (−1542): módulo `bloom` entero sin usar,
  generado con Claude Code.
- [#827](https://github.com/dashpay/rust-dashcore/pull/827): una constante fija `TARGET_PEERS = 3`
  ignoraba el campo de configuración `max_peers`. Venía del commit inicial del cliente SPV, generado
  con Claude Code.
- [#1016](https://github.com/dashpay/rust-dashcore/pull/1016) (+13/−772): re-escaneos duplicados y
  costosos. La sincronización bajó de 21–27 min a 6–9 min.
- [#1041](https://github.com/dashpay/rust-dashcore/pull/1041) (+4/−5783): fallo de seguridad en PSBT
  resuelto borrando el módulo. No lo usaba nadie y sus ejemplos nunca habían compilado.
- [#1094](https://github.com/dashpay/rust-dashcore/pull/1094) (+3563/−5996): una segunda fuente de
  verdad para los bloques dejaba sin verificar 20 de 24 quorums. Tras el cambio, 24/24.
- [#744](https://github.com/dashpay/rust-dashcore/pull/744) (+588/−1116): cada tipo de transacción
  repetía la selección de monedas, el cambio, la comisión y la firma. Se centralizó en
  `TransactionBuilder`.
- [#727](https://github.com/dashpay/rust-dashcore/pull/727) (+285/−489): la construcción y firma de
  transacciones vivía en la FFI. Se movió a la wallet.
- [#1101](https://github.com/dashpay/rust-dashcore/pull/1101) (+213/−382): carrera al parar el
  cliente mientras arranca. Se arregló simplificando el ciclo de vida.
- Limpiezas:
  - [#468](https://github.com/dashpay/rust-dashcore/pull/468) (−1600): tests FFI rotos.
  - [#670](https://github.com/dashpay/rust-dashcore/pull/670): errores FFI.
  - [#377](https://github.com/dashpay/rust-dashcore/pull/377) y
    [#449](https://github.com/dashpay/rust-dashcore/pull/449): funciones FFI sin uso.
  - [#371](https://github.com/dashpay/rust-dashcore/pull/371): tests duplicados.
  - [#828](https://github.com/dashpay/rust-dashcore/pull/828): lógica duplicada.

### platform (iOS / Swift SDK)

- [#3553](https://github.com/dashpay/platform/pull/3553) (+561/−4226): **el mejor ejemplo.**
  - En [#3500](https://github.com/dashpay/platform/pull/3500) ya había quitado las firmas FFI
    escritas a mano en Swift y obligado a usar el header que genera Rust.
  - Ocho días después, una PR hecha con Claude volvió a meterlas (*"Swift uses @_silgen_name shims
    … as the source of truth"*).
  - Con ellas volvieron los errores: 18 fallos de compilación contra el ABI real, un
    `EXC_BAD_ACCESS` por un struct de 152 frente a 136 bytes, y *"guards"* para detectar la
    divergencia.
  - Volví a quitarlas y a imponer el header. El 66 % de las líneas borradas venían de commits de
    Claude.
- [#3500](https://github.com/dashpay/platform/pull/3500) (+179/−1357): las firmas a mano estaban
  desfasadas.
  - A una función le faltaba el argumento `network`, así que todos los argumentos llegaban
    desplazados.
  - `bytes_free` no recibía la longitud y liberaba memoria mal.
  - Además, rs-sdk-ffi reexportaba símbolos de otra capa.
- [#3566](https://github.com/dashpay/platform/pull/3566) (+4387/−11608): gestión de errores FFI
  inconsistente y comprobaciones de nulos repartidas a mano. Pasó a un único tipo de resultado con
  macros. El 72 % de lo borrado venía de commits de Claude.
- [#3079](https://github.com/dashpay/platform/pull/3079) (+296/−3393): el saldo se mostraba mal por
  tener lógica de negocio en Swift en lugar de en Rust. Al limpiar aparecieron más fallos: wallets
  guardadas dos veces, el cliente SPV que no se liberaba y un crash al borrar una wallet.
- [#3567](https://github.com/dashpay/platform/pull/3567) (+428/−2464): cuatro formas distintas de
  representar la red (enteros, `DashSDKNetwork`, `PlatformNetwork`…). Se unificaron en una.
- [#3401](https://github.com/dashpay/platform/pull/3401) (+453/−3788): la fusión manual de
  bibliotecas estáticas se sustituyó por un crate que las enlaza.
- [#3479](https://github.com/dashpay/platform/pull/3479) (+172/−4257): tests de API que ya no
  existía, ejecutados con mocks y fuera de la CI. Ahora corren en la CI.
- [#3425](https://github.com/dashpay/platform/pull/3425) (−74): un módulo cuya única función lanzaba
  `notImplemented`.

## Mensajes para la charla

- La IA tiende a **añadir**: reimplementa lo que ya existe, pone la lógica en la capa equivocada y
  parchea síntomas.
- Quien conoce la estructura **quita**: reutiliza, mueve cada cosa a su sitio y ataca la causa raíz.
- Sin reglas que lo impongan (header generado, CI, revisión), la IA vuelve a meter lo que ya se
  quitó (#3500 → #3553).
- Revisar no es aprobar: varias de estas PRs se aprobaron sin un solo comentario.
