package ltd.redeye.vanguard.common.config.file.messages

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class AlertsMessages(
    var temporarilyBanned: String = "Player <#22D3EE><player></#22D3EE> was banned by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>",
    var permanentlyBanned: String = "Player <#22D3EE><player></#22D3EE> was banned by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var temporarilyIpBanned: String = "Player <#22D3EE><player></#22D3EE> was IP banned by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>",
    var permanentlyIpBanned: String = "Player <#22D3EE><player></#22D3EE> was IP banned by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var temporarilyMuted: String = "Player <#22D3EE><player></#22D3EE> was muted by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>",
    var permanentlyMuted: String = "Player <#22D3EE><player></#22D3EE> was muted by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var temporarilyIpMuted: String = "Player <#22D3EE><player></#22D3EE> was IP muted by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>",
    var permanentlyIpMuted: String = "Player <#22D3EE><player></#22D3EE> was IP muted by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var kicked: String = "Player <#22D3EE><player></#22D3EE> was kicked by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var warned: String = "Player <#22D3EE><player></#22D3EE> was warned by <#22D3EE><origin></#22D3EE>: <white><reason></white>",
    var attemptedToJoinWhileBanned: String = "Player <#22D3EE><player></#22D3EE> attempted to join while banned <hover:show_text:'<#22D3EE><reason></#22D3EE>'><italic>(details)</italic></hover>",
    var attemptedToJoinWhileIpBanned: String = "Player <#22D3EE><player></#22D3EE> attempted to join on an IP address which is banned <hover:show_text:'<#22D3EE><reason></#22D3EE>'><italic>(details)</italic></hover>",
    var anotherAccountOnIpIsBanned: String = "<#22D3EE><bold>Vanguard</bold></#22D3EE> <#64748B>»</#64748B> <#D6D3D1>Player <#22D3EE><player></#22D3EE> shares an IP address with a banned player <hover:show_text:'<#22D3EE><target>\\n</#22D3EE><#D6D3D1>Banned Until:</#D6D3D1> <white><expires></white><#D6D3D1>Banned By:</#D6D3D1> <white><origin></white>\\n<#D6D3D1>Reason:</#D6D3D1> <white><reason></white>'><italic><color:#787878>(details)</color></italic></hover>",
)