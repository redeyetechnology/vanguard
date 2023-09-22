/*
 * Vanguard
 * Copyright (C) 2023 RedEye Technologies Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ltd.redeye.vanguard.common.config.file.messages

import ltd.redeye.vanguard.common.message.VanguardMessage
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class AlertsMessages(
    var temporarilyBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was banned by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>"),
    var permanentlyBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was banned by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var temporarilyIpBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was IP banned by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>"),
    var permanentlyIpBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was IP banned by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var temporarilyMuted: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was muted by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>"),
    var permanentlyMuted: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was muted by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var temporarilyIpMuted: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was IP muted by <#22D3EE><origin></#22D3EE> for <#22D3EE><duration></#22D3EE>: <white><reason></white>"),
    var permanentlyIpMuted: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was IP muted by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var kicked: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was kicked by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var warned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> was warned by <#22D3EE><origin></#22D3EE>: <white><reason></white>"),
    var attemptedToJoinWhileBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> attempted to join while banned <hover:show_text:'<#22D3EE><reason></#22D3EE>'><italic>(details)</italic></hover>"),
    var attemptedToJoinWhileIpBanned: VanguardMessage = VanguardMessage("Player <#22D3EE><player></#22D3EE> attempted to join on an IP address which is banned <hover:show_text:'<#22D3EE><reason></#22D3EE>'><italic>(details)</italic></hover>"),
    var anotherAccountOnIpIsBanned: VanguardMessage = VanguardMessage("<#22D3EE><bold>Vanguard</bold></#22D3EE> <#64748B>»</#64748B> <#D6D3D1>Player <#22D3EE><player></#22D3EE> shares an IP address with a banned player <hover:show_text:'<#22D3EE><target>\\n</#22D3EE><#D6D3D1>Banned Until:</#D6D3D1> <white><expires></white><#D6D3D1>Banned By:</#D6D3D1> <white><origin></white>\\n<#D6D3D1>Reason:</#D6D3D1> <white><reason></white>'><italic><color:#787878>(details)</color></italic></hover>"),
)