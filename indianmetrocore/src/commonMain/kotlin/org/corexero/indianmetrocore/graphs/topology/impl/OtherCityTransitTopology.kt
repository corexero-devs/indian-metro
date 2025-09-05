package org.corexero.indianmetrocore.graphs.topology.impl

import org.corexero.indianmetrocore.graphs.model.City
import org.corexero.indianmetrocore.graphs.model.GraphEdge
import org.corexero.indianmetrocore.graphs.model.LabelledGraphEdge
import org.corexero.indianmetrocore.graphs.model.SourceAndDestination

class OtherCityTransitTopology(val topologyId: Int) :
    org.corexero.indianmetrocore.graphs.topology.CityTransitTopology {

    override val cityName: String
        get() = getCity()

    override val edges: List<GraphEdge>
        get() = getEdgesForCity()

    override fun aliasesOf(stationCode: String): List<String> {
        return aliasGroups()[stationCode] ?: emptyList()
    }

    override fun edgeLineLabels(): Map<SourceAndDestination, List<LabelledGraphEdge>> {
        return when (topologyId) {
            else -> emptyMap()
        }
    }

    override fun stationLines(): Map<String, List<String>> {
        return when (topologyId) {
            0 -> mapOf(
                "M-THLTJG" to listOf("Blue (East-West)"),
                "M-THLT" to listOf("Blue (East-West)"),
                "M-DRD" to listOf("Blue (East-West)"),
                "M-GRK" to listOf("Blue (East-West)"),
                "M-GJR" to listOf("Blue (East-West)"),
                "M-CMM" to listOf("Blue (East-West)"),
                "M-SPS" to listOf("Blue (East-West)"),
                "M-OLDHG" to listOf("Blue (East-West)", "Red (North-South)"),
                "M-SHHP" to listOf("Blue (East-West)"),
                "M-GHK" to listOf("Blue (East-West)"),
                "M-KLPR" to listOf("Blue (East-West)"),
                "M-KNK" to listOf("Blue (East-West)"),
                "M-APP" to listOf("Blue (East-West)"),
                "M-AMRV" to listOf("Blue (East-West)"),
                "M-RBR" to listOf("Blue (East-West)"),
                "M-VSTR" to listOf("Blue (East-West)"),
                "M-NRNT" to listOf("Blue (East-West)"),
                "M-VST" to listOf("Blue (East-West)"),
                "M-APM" to listOf("Red (North-South)"),
                "M-JVR" to listOf("Red (North-South)"),
                "M-RJVN" to listOf("Red (North-South)"),
                "M-SHR" to listOf("Red (North-South)"),
                "M-PLD" to listOf("Red (North-South)"),
                "M-GNDHG" to listOf("Red (North-South)"),
                "M-USM" to listOf("Red (North-South)"),
                "M-VJYN" to listOf("Red (North-South)"),
                "M-VDJ" to listOf("Red (North-South)"),
                "M-RNP" to listOf("Red (North-South)"),
                "M-SBRM" to listOf("Red (North-South)"),
                "M-AC" to listOf("Red (North-South)"),
                "M-SBR" to listOf("Red (North-South)"),
                "M-MTRS" to listOf("Red (North-South)", "Yellow"),
                "M-KTS" to listOf("Yellow"),
                "M-VSHWK" to listOf("Yellow"),
                "M-TPV" to listOf("Yellow"),
                "M-NRMD" to listOf("Yellow"),
                "M-KBC" to listOf("Yellow"),
                "M-GNLZ" to listOf("Yellow", "Violet"),
                "M-RYS" to listOf("Yellow"),
                "M-RND" to listOf("Yellow"),
                "M-DHLK" to listOf("Yellow"),
                "M-INF" to listOf("Yellow"),
                "M-SCTR1Z" to listOf("Yellow"),
                "M-SCTR10" to listOf("Yellow"),
                "M-SCHV" to listOf("Yellow"),
                "M-GFT" to listOf("Violet"),
                "M-PD" to listOf("Violet")
            )

            1 -> mapOf(
                "M-TJS" to listOf("Yellow"),
                "M-SHHDC" to listOf("Yellow"),
                "M-FTH" to listOf("Yellow"),
                "M-TJM" to listOf("Yellow"),
                "M-AGRF" to listOf("Yellow"),
                "M-MNKM" to listOf("Yellow")
            )

            2 -> mapOf(
                "M-CHL" to listOf("Purple"),
                "M-KNGR" to listOf("Purple"),
                "M-KNG" to listOf("Purple"),
                "M-PTTN" to listOf("Purple"),
                "M-JNN" to listOf("Purple"),
                "M-RJRJ" to listOf("Purple"),
                "M-NYN" to listOf("Purple"),
                "M-MYS" to listOf("Purple"),
                "M-DPN" to listOf("Purple"),
                "M-ATT" to listOf("Purple"),
                "M-VJY" to listOf("Purple"),
                "M-SRB" to listOf("Purple"),
                "M-MGD" to listOf("Purple"),
                "M-KRN" to listOf("Purple"),
                "M-NDP" to listOf("Purple", "Green"),
                "M-SRMV" to listOf("Purple"),
                "M-DRB" to listOf("Purple"),
                "M-CBB" to listOf("Purple"),
                "M-MHT" to listOf("Purple"),
                "M-TRN" to listOf("Purple"),
                "M-HLS" to listOf("Purple"),
                "M-INDRN" to listOf("Purple"),
                "M-SWM" to listOf("Purple"),
                "M-BYP" to listOf("Purple"),
                "M-BNN" to listOf("Purple"),
                "M-KRP" to listOf("Purple"),
                "M-SNG" to listOf("Purple"),
                "M-GRDC" to listOf("Purple"),
                "M-HD" to listOf("Purple"),
                "M-STHR" to listOf("Purple"),
                "M-KNDL" to listOf("Purple"),
                "M-NLL" to listOf("Purple"),
                "M-SRS" to listOf("Purple"),
                "M-PTT" to listOf("Purple"),
                "M-KDG" to listOf("Purple"),
                "M-HPF" to listOf("Purple"),
                "M-WHT" to listOf("Purple"),
                "M-SLK" to listOf("Green"),
                "M-THL" to listOf("Green"),
                "M-VJR" to listOf("Green"),
                "M-DDD" to listOf("Green"),
                "M-KNN" to listOf("Green"),
                "M-YLC" to listOf("Green"),
                "M-JYPR" to listOf("Green"),
                "M-BNS" to listOf("Green"),
                "M-RSHT" to listOf("Green"),
                "M-JYN" to listOf("Green"),
                "M-STHN" to listOf("Green"),
                "M-LLB" to listOf("Green"),
                "M-NTN" to listOf("Green"),
                "M-KRSH" to listOf("Green"),
                "M-CHC" to listOf("Green"),
                "M-MNT" to listOf("Green"),
                "M-SRR" to listOf("Green"),
                "M-MHK" to listOf("Green"),
                "M-RJJ" to listOf("Green"),
                "M-MHL" to listOf("Green"),
                "M-SND" to listOf("Green"),
                "M-YSH" to listOf("Green"),
                "M-GRGNT" to listOf("Green"),
                "M-PNY" to listOf("Green"),
                "M-PNYN" to listOf("Green"),
                "M-JLH" to listOf("Green"),
                "M-DSR" to listOf("Green"),
                "M-NGS" to listOf("Green"),
                "M-MNJ" to listOf("Green"),
                "M-CHKKB" to listOf("Green"),
                "M-MDV" to listOf("Green")
            )

            4 -> mapOf(
                "M-STTH" to listOf("Green"),
                "M-ARG" to listOf("Green", "Inter-Corridor", "Blue"),
                "M-EKKT" to listOf("Green", "Inter-Corridor"),
                "M-ASHKNG" to listOf("Green", "Inter-Corridor"),
                "M-VDPL" to listOf("Green", "Inter-Corridor"),
                "M-ARMB" to listOf("Green", "Inter-Corridor"),
                "M-PRTCHT" to listOf("Green", "Inter-Corridor"),
                "M-KYMB" to listOf("Green", "Inter-Corridor"),
                "M-THRMN" to listOf("Green", "Inter-Corridor"),
                "M-ANNNGR" to listOf("Green", "Inter-Corridor"),
                "M-ANNNGRS" to listOf("Green", "Inter-Corridor"),
                "M-SHNYN" to listOf("Green", "Inter-Corridor"),
                "M-PCHY" to listOf("Green", "Inter-Corridor"),
                "M-KLPK" to listOf("Green", "Inter-Corridor"),
                "M-NHRPR" to listOf("Green", "Inter-Corridor"),
                "M-EGMR" to listOf("Green", "Inter-Corridor"),
                "M-PRTCHTH" to listOf("Green", "Inter-Corridor", "Blue"),
                "M-ARPR" to listOf("Inter-Corridor", "Blue"),
                "M-MNMB" to listOf("Inter-Corridor", "Blue"),
                "M-OTN" to listOf("Inter-Corridor", "Blue"),
                "M-WMCNG" to listOf("Blue"),
                "M-WMCNGR" to listOf("Blue"),
                "M-THRVT" to listOf("Blue"),
                "M-THRVTR" to listOf("Blue"),
                "M-KLDP" to listOf("Blue"),
                "M-TLLG" to listOf("Blue"),
                "M-NWWS" to listOf("Blue"),
                "M-TNDR" to listOf("Blue"),
                "M-SRTHY" to listOf("Blue"),
                "M-WSHR" to listOf("Blue"),
                "M-MNND" to listOf("Blue"),
                "M-HGHC" to listOf("Blue"),
                "M-GVRN" to listOf("Blue"),
                "M-LCM" to listOf("Blue"),
                "M-THSN" to listOf("Blue"),
                "M-AGDM" to listOf("Blue"),
                "M-TYNM" to listOf("Blue"),
                "M-NNDN" to listOf("Blue"),
                "M-SDPT" to listOf("Blue"),
                "M-LTTL" to listOf("Blue"),
                "M-GNDYM" to listOf("Blue")
            )

            6 -> mapOf(
                "M-LBNG" to listOf("Red"),
                "M-VCTR" to listOf("Red"),
                "M-CHTN" to listOf("Red"),
                "M-DLSKH" to listOf("Red"),
                "M-MSRM" to listOf("Red"),
                "M-NWMR" to listOf("Red"),
                "M-MLKP" to listOf("Red"),
                "M-MHTM" to listOf("Red", "Green"),
                "M-OSMN" to listOf("Red"),
                "M-GNDHBH" to listOf("Red"),
                "M-NMPL" to listOf("Red"),
                "M-ASSM" to listOf("Red"),
                "M-LKDK" to listOf("Red"),
                "M-KHRTB" to listOf("Red"),
                "M-ERRM" to listOf("Red"),
                "M-PNJGT" to listOf("Red"),
                "M-AMRP" to listOf("Red", "Blue"),
                "M-SRNG" to listOf("Red"),
                "M-ESHS" to listOf("Red"),
                "M-ERRG" to listOf("Red"),
                "M-BHRTN" to listOf("Red"),
                "M-MSPT" to listOf("Red"),
                "M-BLN" to listOf("Red"),
                "M-KKTP" to listOf("Red"),
                "M-KPHB" to listOf("Red"),
                "M-JNTC" to listOf("Red"),
                "M-MYPRZ" to listOf("Red"),
                "M-JBSP" to listOf("Green"),
                "M-SCNDR" to listOf("Green"),
                "M-GNDHH" to listOf("Green"),
                "M-MSHR" to listOf("Green"),
                "M-RTCC" to listOf("Green"),
                "M-CHKKD" to listOf("Green"),
                "M-NRYN" to listOf("Green"),
                "M-SLTNB" to listOf("Green"),
                "M-NGLZ" to listOf("Blue"),
                "M-UPPL" to listOf("Blue"),
                "M-STDM" to listOf("Blue"),
                "M-NGRZ" to listOf("Blue"),
                "M-HBSG" to listOf("Blue"),
                "M-TRNKZ" to listOf("Blue"),
                "M-MTTG" to listOf("Blue"),
                "M-SCNDRB" to listOf("Blue"),
                "M-PRDGR" to listOf("Blue"),
                "M-PRDS" to listOf("Blue"),
                "M-RSLP" to listOf("Blue"),
                "M-PRKS" to listOf("Blue"),
                "M-BGMP" to listOf("Blue"),
                "M-MDHRN" to listOf("Blue"),
                "M-YSFG" to listOf("Blue"),
                "M-RDN5" to listOf("Blue"),
                "M-JBLH" to listOf("Blue"),
                "M-PDDM" to listOf("Blue"),
                "M-MDHP" to listOf("Blue"),
                "M-DRGMC" to listOf("Blue"),
                "M-HTCC" to listOf("Blue"),
                "M-RDRG" to listOf("Blue")
            )

            7 -> mapOf(
                "M-BICP" to listOf("Mansarovar - Badi Chaupar"),
                "M-CTCP" to listOf("Mansarovar - Badi Chaupar"),
                "M-CDPE" to listOf("Mansarovar - Badi Chaupar"),
                "M-SICP" to listOf("Mansarovar - Badi Chaupar"),
                "M-MRSN" to listOf("Mansarovar - Badi Chaupar"),
                "M-CLJP" to listOf("Mansarovar - Badi Chaupar"),
                "M-RMNR" to listOf("Mansarovar - Badi Chaupar"),
                "M-SMNR" to listOf("Mansarovar - Badi Chaupar"),
                "M-VKVR" to listOf("Mansarovar - Badi Chaupar"),
                "M-NAMT" to listOf("Mansarovar - Badi Chaupar"),
                "M-MSOR" to listOf("Mansarovar - Badi Chaupar")
            )

            8 -> mapOf(
                "M-ITKN" to listOf("Orange"),
                "M-KLYNP" to listOf("Orange"),
                "M-SPMH" to listOf("Orange"),
                "M-VSHWVDY" to listOf("Orange"),
                "M-GRDVC" to listOf("Orange"),
                "M-GTN" to listOf("Orange"),
                "M-RWT" to listOf("Orange"),
                "M-LLRH" to listOf("Orange"),
                "M-MTJH" to listOf("Orange"),
                "M-CHNN" to listOf("Orange"),
                "M-NVN" to listOf("Orange"),
                "M-BDC" to listOf("Orange"),
                "M-NYG" to listOf("Orange"),
                "M-KNP" to listOf("Orange")
            )

            9 -> mapOf(
                "M-ALV" to listOf("KMRL"),
                "M-PLN" to listOf("KMRL"),
                "M-CMP" to listOf("KMRL"),
                "M-AMB" to listOf("KMRL"),
                "M-MTTM" to listOf("KMRL"),
                "M-KLM" to listOf("KMRL"),
                "M-CCH" to listOf("KMRL"),
                "M-PTHDP" to listOf("KMRL"),
                "M-EDP" to listOf("KMRL"),
                "M-CHNG" to listOf("KMRL"),
                "M-PLR" to listOf("KMRL"),
                "M-JLNS" to listOf("KMRL"),
                "M-KLR" to listOf("KMRL"),
                "M-TWN" to listOf("KMRL"),
                "M-MGRDZ" to listOf("KMRL"),
                "M-MHRJ" to listOf("KMRL"),
                "M-ERN" to listOf("KMRL"),
                "M-KDV" to listOf("KMRL"),
                "M-ELM" to listOf("KMRL"),
                "M-VYT" to listOf("KMRL"),
                "M-THY" to listOf("KMRL"),
                "M-PTTH" to listOf("KMRL"),
                "M-VDK" to listOf("KMRL"),
                "M-SNJ" to listOf("KMRL"),
                "M-TRP" to listOf("KMRL")
            )

            11 -> mapOf(
                "M-KKVSZ" to listOf("Orange", "Blue"),
                "M-KSJR" to listOf("Orange"),
                "M-KJNN" to listOf("Orange"),
                "M-KKSK" to listOf("Orange"),
                "M-KHMD" to listOf("Orange"),
                "M-KJKA" to listOf("Purple"),
                "M-KTKP" to listOf("Purple"),
                "M-KSKB" to listOf("Purple"),
                "M-KBCR" to listOf("Purple"),
                "M-KBBR" to listOf("Purple"),
                "M-KTRT" to listOf("Purple"),
                "M-KMJHZ" to listOf("Purple"),
                "M-HWMM" to listOf("Green"),
                "M-HWHM" to listOf("Green"),
                "M-MKNA" to listOf("Green"),
                "M-KESPZ" to listOf("Green", "Blue"),
                "M-SVSA" to listOf("Green"),
                "M-KESAZ" to listOf("Green"),
                "M-CPSAZ" to listOf("Green"),
                "M-CCSCZ" to listOf("Green"),
                "M-BCSDZ" to listOf("Green"),
                "M-SSSAZ" to listOf("Green"),
                "M-PBGBZ" to listOf("Green"),
                "M-SDHMZ" to listOf("Green"),
                "M-KDSWZ" to listOf("Blue"),
                "M-KBAR" to listOf("Blue"),
                "M-KNAPZ" to listOf("Blue"),
                "M-KDMIZ" to listOf("Blue"),
                "M-KBELZ" to listOf("Blue"),
                "M-KSHYZ" to listOf("Blue"),
                "M-KSHOZ" to listOf("Blue"),
                "M-KGPKZ" to listOf("Blue"),
                "M-KMHRZ" to listOf("Blue"),
                "M-KCENZ" to listOf("Blue"),
                "M-KCWCZ" to listOf("Blue"),
                "M-KPSKZ" to listOf("Blue"),
                "M-KMDIZ" to listOf("Blue"),
                "M-KRSDZ" to listOf("Blue"),
                "M-KNBNZ" to listOf("Blue"),
                "M-KJPKZ" to listOf("Blue"),
                "M-KKHGZ" to listOf("Blue"),
                "M-KRSBZ" to listOf("Blue"),
                "M-KMUKZ" to listOf("Blue"),
                "M-KNTJZ" to listOf("Blue"),
                "M-KMSNZ" to listOf("Blue"),
                "M-KGTNZ" to listOf("Blue"),
                "M-KKNZ" to listOf("Blue"),
                "M-KSKDZ" to listOf("Blue")
            )

            12 -> mapOf(
                "M-MNSHP" to listOf("Red"),
                "M-INDRNGR" to listOf("Red"),
                "M-BHTN" to listOf("Red"),
                "M-LKHR" to listOf("Red"),
                "M-BDSH" to listOf("Red"),
                "M-ITCL" to listOf("Red"),
                "M-VSHV" to listOf("Red"),
                "M-KDSN" to listOf("Red"),
                "M-HZRTG" to listOf("Red"),
                "M-SCH" to listOf("Red"),
                "M-HSS" to listOf("Red"),
                "M-CHRBG" to listOf("Red"),
                "M-DRGPR" to listOf("Red"),
                "M-MWYZ" to listOf("Red"),
                "M-ALMBG" to listOf("Red"),
                "M-ALMBGH" to listOf("Red"),
                "M-SNGRN" to listOf("Red"),
                "M-KRSHNN" to listOf("Red"),
                "M-TRNSP" to listOf("Red"),
                "M-AMSZZ" to listOf("Red"),
                "M-CCSR" to listOf("Red")
            )

            14 -> mapOf(
                "M-DHS" to listOf("RedYellowLine"),
                "M-OVR" to listOf("RedYellowLine"),
                "M-RSH" to listOf("RedYellowLine"),
                "M-DVP" to listOf("RedYellowLine"),
                "M-MGT" to listOf("RedYellowLine"),
                "M-PSR" to listOf("RedYellowLine"),
                "M-AKR" to listOf("RedYellowLine"),
                "M-KRR" to listOf("RedYellowLine"),
                "M-DND" to listOf("RedYellowLine"),
                "M-ARY" to listOf("RedYellowLine"),
                "M-GRGN" to listOf("RedYellowLine"),
                "M-JGS" to listOf("RedYellowLine"),
                "M-MGR" to listOf("RedYellowLine"),
                "M-GND" to listOf("RedYellowLine"),
                "M-ANDH" to listOf("RedYellowLine"),
                "M-LWRM" to listOf("RedYellowLine"),
                "M-OSH" to listOf("RedYellowLine"),
                "M-GRG" to listOf("RedYellowLine"),
                "M-BNG" to listOf("RedYellowLine"),
                "M-LWR" to listOf("RedYellowLine"),
                "M-MLD" to listOf("RedYellowLine"),
                "M-VLN" to listOf("RedYellowLine"),
                "M-DHN" to listOf("RedYellowLine"),
                "M-KNDV" to listOf("RedYellowLine"),
                "M-SHM" to listOf("RedYellowLine"),
                "M-BRV" to listOf("RedYellowLine"),
                "M-EKS" to listOf("RedYellowLine"),
                "M-MND" to listOf("RedYellowLine"),
                "M-KNDR" to listOf("RedYellowLine"),
                "M-ANN" to listOf("RedYellowLine"),
                "M-VRS" to listOf("BlueLine"),
                "M-DNN" to listOf("BlueLine"),
                "M-AZD" to listOf("BlueLine"),
                "M-AND" to listOf("BlueLine"),
                "M-WST" to listOf("BlueLine"),
                "M-CHK" to listOf("BlueLine"),
                "M-ARP" to listOf("BlueLine"),
                "M-MNK" to listOf("BlueLine", "AquaLine"),
                "M-SKN" to listOf("BlueLine"),
                "M-ASL" to listOf("BlueLine"),
                "M-JGR" to listOf("BlueLine"),
                "M-GHT" to listOf("BlueLine"),
                "M-BLP" to listOf("NaviMumbaiLine"),
                "M-RBC" to listOf("NaviMumbaiLine"),
                "M-BLPD" to listOf("NaviMumbaiLine"),
                "M-UTS" to listOf("NaviMumbaiLine"),
                "M-KND" to listOf("NaviMumbaiLine"),
                "M-KHR" to listOf("NaviMumbaiLine"),
                "M-CNT" to listOf("NaviMumbaiLine"),
                "M-PTH" to listOf("NaviMumbaiLine"),
                "M-AMN" to listOf("NaviMumbaiLine"),
                "M-PTHL" to listOf("NaviMumbaiLine"),
                "M-PND" to listOf("NaviMumbaiLine"),
                "M-ACA" to listOf("AquaLine"),
                "M-WOR" to listOf("AquaLine"),
                "M-SIDV" to listOf("AquaLine"),
                "M-DDRM" to listOf("AquaLine"),
                "M-SDIT" to listOf("AquaLine"),
                "M-DHAV" to listOf("AquaLine"),
                "M-BDRM" to listOf("AquaLine"),
                "M-VIDN" to listOf("AquaLine"),
                "M-STZM" to listOf("AquaLine"),
                "M-CIAD" to listOf("AquaLine"),
                "M-SHRR" to listOf("AquaLine"),
                "M-CSIA" to listOf("AquaLine"),
                "M-MIDC" to listOf("AquaLine"),
                "M-SEPZ" to listOf("AquaLine"),
                "M-ARYJ" to listOf("AquaLine"),
                "M-SNTG" to listOf("MumbaiMonorail"),
                "M-LWRP" to listOf("MumbaiMonorail"),
                "M-MNTC" to listOf("MumbaiMonorail"),
                "M-AMBD" to listOf("MumbaiMonorail"),
                "M-NGN" to listOf("MumbaiMonorail"),
                "M-DDR" to listOf("MumbaiMonorail"),
                "M-WDL" to listOf("MumbaiMonorail"),
                "M-ACH" to listOf("MumbaiMonorail"),
                "M-ANT" to listOf("MumbaiMonorail"),
                "M-GTBN" to listOf("MumbaiMonorail"),
                "M-WDLD" to listOf("MumbaiMonorail"),
                "M-BHKT" to listOf("MumbaiMonorail"),
                "M-MYSR" to listOf("MumbaiMonorail"),
                "M-BHRT" to listOf("MumbaiMonorail"),
                "M-FRT" to listOf("MumbaiMonorail"),
                "M-VNP" to listOf("MumbaiMonorail"),
                "M-CHM" to listOf("MumbaiMonorail")
            )

            15 -> mapOf(
                "M-ATM" to listOf("Orange"),
                "M-NRRD" to listOf("Orange"),
                "M-KDBS" to listOf("Orange"),
                "M-GDDG" to listOf("Orange"),
                "M-KSTR" to listOf("Orange"),
                "M-ZRML" to listOf("Orange"),
                "M-STBLDZ" to listOf("Orange", "Aqua"),
                "M-CNGR" to listOf("Orange"),
                "M-RHTC" to listOf("Orange"),
                "M-AJNS" to listOf("Orange"),
                "M-CHHTRP" to listOf("Orange"),
                "M-JPRK" to listOf("Orange"),
                "M-UJWL" to listOf("Orange"),
                "M-ARPRTZ" to listOf("Orange"),
                "M-ARPRTS" to listOf("Orange"),
                "M-NWRP" to listOf("Orange"),
                "M-KHPR" to listOf("Orange"),
                "M-LKMN" to listOf("Aqua"),
                "M-BNSNG" to listOf("Aqua"),
                "M-VSDV" to listOf("Aqua"),
                "M-RCHN" to listOf("Aqua"),
                "M-SBHS" to listOf("Aqua"),
                "M-DHRM" to listOf("Aqua"),
                "M-LDSQ" to listOf("Aqua"),
                "M-SHNKR" to listOf("Aqua"),
                "M-INST" to listOf("Aqua"),
                "M-JHNSR" to listOf("Aqua"),
                "M-CTTN" to listOf("Aqua"),
                "M-NGPR" to listOf("Aqua"),
                "M-DSRVS" to listOf("Aqua"),
                "M-AGRS" to listOf("Aqua"),
                "M-CHTRL" to listOf("Aqua"),
                "M-TLPH" to listOf("Aqua"),
                "M-AMBDKR" to listOf("Aqua"),
                "M-VSHND" to listOf("Aqua"),
                "M-PRJP" to listOf("Aqua")
            )

            16 -> mapOf(
                "M-SWR" to listOf("Purple"),
                "M-MNDZ" to listOf("Purple"),
                "M-KSB" to listOf("Purple"),
                "M-CVLC" to listOf("Purple"),
                "M-SHVJN" to listOf("Purple"),
                "M-BPD" to listOf("Purple"),
                "M-DPD" to listOf("Purple"),
                "M-PHG" to listOf("Purple"),
                "M-KSR" to listOf("Purple"),
                "M-BHS" to listOf("Purple"),
                "M-SNTT" to listOf("Purple"),
                "M-PCM" to listOf("Purple"),
                "M-VNZ" to listOf("Aqua"),
                "M-ANNDN" to listOf("Aqua"),
                "M-IDL" to listOf("Aqua"),
                "M-NLS" to listOf("Aqua"),
                "M-GRW" to listOf("Aqua"),
                "M-DCC" to listOf("Aqua"),
                "M-CHHT" to listOf("Aqua"),
                "M-PNM" to listOf("Aqua"),
                "M-CVLCR" to listOf("Aqua"),
                "M-MNG" to listOf("Aqua"),
                "M-PNR" to listOf("Aqua"),
                "M-RBH" to listOf("Aqua"),
                "M-BND" to listOf("Aqua"),
                "M-YRW" to listOf("Aqua"),
                "M-KLY" to listOf("Aqua"),
                "M-RMW" to listOf("Aqua")
            )

            else -> emptyMap()
        }
    }

    override fun aliasGroups(): Map<String, List<String>> {
        return when (topologyId) {
            0 -> emptyMap()
            1 -> emptyMap()
            2 -> emptyMap()
            4 -> emptyMap()
            6 -> mapOf(
                "M-PRDGR" to listOf("M-JBSP"),
                "M-JBSP" to listOf("M-PRDGR")
            )

            7 -> emptyMap()
            8 -> emptyMap()
            9 -> emptyMap()
            11 -> emptyMap()
            12 -> emptyMap()
            14 -> mapOf(
                "M-ANDH" to listOf("M-DNN"),
                "M-DNN" to listOf("M-ANDH"),
                "M-WST" to listOf("M-GND"),
                "M-GND" to listOf("M-WST")
            )

            15 -> emptyMap()
            16 -> mapOf(
                "M-CVLC" to listOf("M-CVLCR"),
                "M-CVLCR" to listOf("M-CVLC")
            )

            else -> emptyMap()
        }
    }

    private fun getEdgesForCity(): List<GraphEdge> {
        return when (topologyId) {
            0 -> listOf(
                GraphEdge("M-THLTJG", "M-THLT", 1),
                GraphEdge("M-THLT", "M-DRD", 1),
                GraphEdge("M-DRD", "M-GRK", 1),
                GraphEdge("M-GRK", "M-GJR", 1),
                GraphEdge("M-GJR", "M-CMM", 1),
                GraphEdge("M-CMM", "M-SPS", 1),
                GraphEdge("M-SPS", "M-OLDHG", 1),
                GraphEdge("M-OLDHG", "M-SHHP", 1),
                GraphEdge("M-SHHP", "M-GHK", 1),
                GraphEdge("M-GHK", "M-KLPR", 1),
                GraphEdge("M-KLPR", "M-KNK", 1),
                GraphEdge("M-KNK", "M-APP", 1),
                GraphEdge("M-APP", "M-AMRV", 1),
                GraphEdge("M-AMRV", "M-RBR", 1),
                GraphEdge("M-RBR", "M-VSTR", 1),
                GraphEdge("M-VSTR", "M-NRNT", 1),
                GraphEdge("M-NRNT", "M-VST", 1),
                GraphEdge("M-APM", "M-JVR", 1),
                GraphEdge("M-JVR", "M-RJVN", 1),
                GraphEdge("M-RJVN", "M-SHR", 1),
                GraphEdge("M-SHR", "M-PLD", 1),
                GraphEdge("M-PLD", "M-GNDHG", 1),
                GraphEdge("M-GNDHG", "M-OLDHG", 1),
                GraphEdge("M-OLDHG", "M-USM", 1),
                GraphEdge("M-USM", "M-VJYN", 1),
                GraphEdge("M-VJYN", "M-VDJ", 1),
                GraphEdge("M-VDJ", "M-RNP", 1),
                GraphEdge("M-RNP", "M-SBRM", 1),
                GraphEdge("M-SBRM", "M-AC", 1),
                GraphEdge("M-AC", "M-SBR", 1),
                GraphEdge("M-SBR", "M-MTRS", 1),
                GraphEdge("M-MTRS", "M-KTS", 1),
                GraphEdge("M-KTS", "M-VSHWK", 1),
                GraphEdge("M-VSHWK", "M-TPV", 1),
                GraphEdge("M-TPV", "M-NRMD", 1),
                GraphEdge("M-NRMD", "M-KBC", 1),
                GraphEdge("M-KBC", "M-GNLZ", 1),
                GraphEdge("M-GNLZ", "M-RYS", 1),
                GraphEdge("M-RYS", "M-RND", 1),
                GraphEdge("M-RND", "M-DHLK", 1),
                GraphEdge("M-DHLK", "M-INF", 1),
                GraphEdge("M-INF", "M-SCTR1Z", 1),
                GraphEdge("M-SCTR1Z", "M-SCTR10", 1),
                GraphEdge("M-SCTR10", "M-SCHV", 1),
                GraphEdge("M-GFT", "M-PD", 1),
                GraphEdge("M-PD", "M-GNLZ", 1),
            )

            1 -> listOf(
                GraphEdge("M-TJS", "M-SHHDC", 1),
                GraphEdge("M-SHHDC", "M-FTH", 1),
                GraphEdge("M-FTH", "M-TJM", 1),
                GraphEdge("M-TJM", "M-AGRF", 1),
                GraphEdge("M-AGRF", "M-MNKM", 1),
            )

            2 -> listOf(
                GraphEdge("M-CHL", "M-KNGR", 1),
                GraphEdge("M-KNGR", "M-KNG", 1),
                GraphEdge("M-KNG", "M-PTTN", 1),
                GraphEdge("M-PTTN", "M-JNN", 1),
                GraphEdge("M-JNN", "M-RJRJ", 1),
                GraphEdge("M-RJRJ", "M-NYN", 1),
                GraphEdge("M-NYN", "M-MYS", 1),
                GraphEdge("M-MYS", "M-DPN", 1),
                GraphEdge("M-DPN", "M-ATT", 1),
                GraphEdge("M-ATT", "M-VJY", 1),
                GraphEdge("M-VJY", "M-SRB", 1),
                GraphEdge("M-SRB", "M-MGD", 1),
                GraphEdge("M-MGD", "M-KRN", 1),
                GraphEdge("M-KRN", "M-NDP", 1),
                GraphEdge("M-NDP", "M-SRMV", 1),
                GraphEdge("M-SRMV", "M-DRB", 1),
                GraphEdge("M-DRB", "M-CBB", 1),

                GraphEdge("M-CBB", "M-MHT", 1),
                GraphEdge("M-MHT", "M-TRN", 1),
                GraphEdge("M-TRN", "M-HLS", 1),

                GraphEdge("M-HLS", "M-INDRN", 1),
                GraphEdge("M-INDRN", "M-SWM", 1),
                GraphEdge("M-SWM", "M-BYP", 1),

                GraphEdge("M-BYP", "M-BNN", 1),
                GraphEdge("M-BNN", "M-KRP", 1),
                GraphEdge("M-KRP", "M-SNG", 1),

                GraphEdge("M-SNG", "M-GRDC", 1),
                GraphEdge("M-GRDC", "M-HD", 1),
                GraphEdge("M-HD", "M-STHR", 1),

                GraphEdge("M-STHR", "M-KNDL", 1),
                GraphEdge("M-KNDL", "M-NLL", 1),
                GraphEdge("M-NLL", "M-SRS", 1),

                GraphEdge("M-SRS", "M-PTT", 1),
                GraphEdge("M-PTT", "M-KDG", 1),
                GraphEdge("M-KDG", "M-HPF", 1),

                GraphEdge("M-HPF", "M-WHT", 1),
                GraphEdge("M-SLK", "M-THL", 1),
                GraphEdge("M-THL", "M-VJR", 1),

                GraphEdge("M-VJR", "M-DDD", 1),
                GraphEdge("M-DDD", "M-KNN", 1),
                GraphEdge("M-KNN", "M-YLC", 1),

                GraphEdge("M-YLC", "M-JYPR", 1),
                GraphEdge("M-JYPR", "M-BNS", 1),
                GraphEdge("M-BNS", "M-RSHT", 1),

                GraphEdge("M-RSHT", "M-JYN", 1),
                GraphEdge("M-JYN", "M-STHN", 1),
                GraphEdge("M-STHN", "M-LLB", 1),

                GraphEdge("M-LLB", "M-NTN", 1),
                GraphEdge("M-NTN", "M-KRSH", 1),
                GraphEdge("M-KRSH", "M-CHC", 1),

                GraphEdge("M-CHC", "M-NDP", 1),
                GraphEdge("M-NDP", "M-MNT", 1),
                GraphEdge("M-MNT", "M-SRR", 1),

                GraphEdge("M-SRR", "M-MHK", 1),
                GraphEdge("M-MHK", "M-RJJ", 1),
                GraphEdge("M-RJJ", "M-MHL", 1),

                GraphEdge("M-MHL", "M-SND", 1),
                GraphEdge("M-SND", "M-YSH", 1),
                GraphEdge("M-YSH", "M-GRGNT", 1),

                GraphEdge("M-GRGNT", "M-PNY", 1),
                GraphEdge("M-PNY", "M-PNYN", 1),
                GraphEdge("M-PNYN", "M-JLH", 1),

                GraphEdge("M-JLH", "M-DSR", 1),
                GraphEdge("M-DSR", "M-NGS", 1),
                GraphEdge("M-NGS", "M-MNJ", 1),

                GraphEdge("M-MNJ", "M-CHKKB", 1),
                GraphEdge("M-CHKKB", "M-MDV", 1)
            )

            4 -> listOf(
                GraphEdge("M-STTH", "M-ARG", 1),
                GraphEdge("M-ARG", "M-EKKT", 1),
                GraphEdge("M-EKKT", "M-ASHKNG", 1),
                GraphEdge("M-ASHKNG", "M-VDPL", 1),
                GraphEdge("M-VDPL", "M-ARMB", 1),
                GraphEdge("M-ARMB", "M-PRTCHT", 1),
                GraphEdge("M-PRTCHT", "M-KYMB", 1),
                GraphEdge("M-KYMB", "M-THRMN", 1),
                GraphEdge("M-THRMN", "M-ANNNGR", 1),
                GraphEdge("M-ANNNGR", "M-ANNNGRS", 1),
                GraphEdge("M-ANNNGRS", "M-SHNYN", 1),
                GraphEdge("M-SHNYN", "M-PCHY", 1),
                GraphEdge("M-PCHY", "M-KLPK", 1),
                GraphEdge("M-KLPK", "M-NHRPR", 1),
                GraphEdge("M-NHRPR", "M-EGMR", 1),
                GraphEdge("M-EGMR", "M-PRTCHTH", 1),
                GraphEdge("M-ARPR", "M-MNMB", 1),
                GraphEdge("M-MNMB", "M-OTN", 1),
                GraphEdge("M-OTN", "M-ARG", 1),
                GraphEdge("M-WMCNG", "M-WMCNGR", 1),
                GraphEdge("M-WMCNGR", "M-THRVT", 1),
                GraphEdge("M-THRVT", "M-THRVTR", 1),
                GraphEdge("M-THRVTR", "M-KLDP", 1),
                GraphEdge("M-KLDP", "M-TLLG", 1),
                GraphEdge("M-TLLG", "M-NWWS", 1),
                GraphEdge("M-NWWS", "M-TNDR", 1),
                GraphEdge("M-TNDR", "M-SRTHY", 1),
                GraphEdge("M-SRTHY", "M-WSHR", 1),
                GraphEdge("M-WSHR", "M-MNND", 1),
                GraphEdge("M-MNND", "M-HGHC", 1),
                GraphEdge("M-HGHC", "M-PRTCHTH", 1),
                GraphEdge("M-PRTCHTH", "M-GVRN", 1),
                GraphEdge("M-GVRN", "M-LCM", 1),
                GraphEdge("M-LCM", "M-THSN", 1),
                GraphEdge("M-THSN", "M-AGDM", 1),
                GraphEdge("M-AGDM", "M-TYNM", 1),
                GraphEdge("M-TYNM", "M-NNDN", 1),
                GraphEdge("M-NNDN", "M-SDPT", 1),
                GraphEdge("M-SDPT", "M-LTTL", 1),
                GraphEdge("M-LTTL", "M-GNDYM", 1),
                GraphEdge("M-GNDYM", "M-ARG", 1)
            )

            6 -> listOf(
                GraphEdge("M-LBNG", "M-VCTR", 1),
                GraphEdge("M-VCTR", "M-CHTN", 1),
                GraphEdge("M-CHTN", "M-DLSKH", 1),
                GraphEdge("M-DLSKH", "M-MSRM", 1),
                GraphEdge("M-MSRM", "M-NWMR", 1),
                GraphEdge("M-NWMR", "M-MLKP", 1),
                GraphEdge("M-MLKP", "M-MHTM", 1),
                GraphEdge("M-MHTM", "M-OSMN", 1),
                GraphEdge("M-OSMN", "M-GNDHBH", 1),
                GraphEdge("M-GNDHBH", "M-NMPL", 1),
                GraphEdge("M-NMPL", "M-ASSM", 1),
                GraphEdge("M-ASSM", "M-LKDK", 1),
                GraphEdge("M-LKDK", "M-KHRTB", 1),
                GraphEdge("M-KHRTB", "M-ERRM", 1),
                GraphEdge("M-ERRM", "M-PNJGT", 1),
                GraphEdge("M-PNJGT", "M-AMRP", 1),
                GraphEdge("M-AMRP", "M-SRNG", 1),
                GraphEdge("M-SRNG", "M-ESHS", 1),
                GraphEdge("M-ESHS", "M-ERRG", 1),
                GraphEdge("M-ERRG", "M-BHRTN", 1),
                GraphEdge("M-BHRTN", "M-MSPT", 1),
                GraphEdge("M-MSPT", "M-BLN", 1),
                GraphEdge("M-BLN", "M-KKTP", 1),
                GraphEdge("M-KKTP", "M-KPHB", 1),
                GraphEdge("M-KPHB", "M-JNTC", 1),
                GraphEdge("M-JNTC", "M-MYPRZ", 1),
                GraphEdge("M-JBSP", "M-SCNDR", 1),
                GraphEdge("M-SCNDR", "M-GNDHH", 1),
                GraphEdge("M-GNDHH", "M-MSHR", 1),
                GraphEdge("M-MSHR", "M-RTCC", 1),
                GraphEdge("M-RTCC", "M-CHKKD", 1),
                GraphEdge("M-CHKKD", "M-NRYN", 1),
                GraphEdge("M-NRYN", "M-SLTNB", 1),
                GraphEdge("M-SLTNB", "M-MHTM", 1),
                GraphEdge("M-NGLZ", "M-UPPL", 1),
                GraphEdge("M-UPPL", "M-STDM", 1),
                GraphEdge("M-STDM", "M-NGRZ", 1),
                GraphEdge("M-NGRZ", "M-HBSG", 1),
                GraphEdge("M-HBSG", "M-TRNKZ", 1),
                GraphEdge("M-TRNKZ", "M-MTTG", 1),
                GraphEdge("M-MTTG", "M-SCNDRB", 1),
                GraphEdge("M-SCNDRB", "M-PRDGR", 1),
                GraphEdge("M-PRDGR", "M-PRDS", 1),
                GraphEdge("M-PRDS", "M-RSLP", 1),
                GraphEdge("M-RSLP", "M-PRKS", 1),
                GraphEdge("M-PRKS", "M-BGMP", 1),
                GraphEdge("M-BGMP", "M-AMRP", 1),
                GraphEdge("M-AMRP", "M-MDHRN", 1),
                GraphEdge("M-MDHRN", "M-YSFG", 1),
                GraphEdge("M-YSFG", "M-RDN5", 1),
                GraphEdge("M-RDN5", "M-JBLH", 1),
                GraphEdge("M-JBLH", "M-PDDM", 1),
                GraphEdge("M-PDDM", "M-MDHP", 1),
                GraphEdge("M-MDHP", "M-DRGMC", 1),
                GraphEdge("M-DRGMC", "M-HTCC", 1),
                GraphEdge("M-RDRG", "M-HTCC", 1),
                GraphEdge("M-PRDGR", "M-JBSP", 0)
            )

            7 -> listOf(
                GraphEdge("M-BICP", "M-CTCP", 1),
                GraphEdge("M-CTCP", "M-CDPE", 1),
                GraphEdge("M-CDPE", "M-SICP", 1),
                GraphEdge("M-SICP", "M-MRSN", 1),
                GraphEdge("M-MRSN", "M-CLJP", 1),
                GraphEdge("M-CLJP", "M-RMNR", 1),
                GraphEdge("M-RMNR", "M-SMNR", 1),
                GraphEdge("M-SMNR", "M-VKVR", 1),
                GraphEdge("M-VKVR", "M-NAMT", 1),
                GraphEdge("M-NAMT", "M-MSOR", 1)
            )

            8 ->
                listOf(
                    GraphEdge("M-ITKN", "M-KLYNP", 1),
                    GraphEdge("M-KLYNP", "M-SPMH", 1),
                    GraphEdge("M-SPMH", "M-VSHWVDY", 1),
                    GraphEdge("M-VSHWVDY", "M-GRDVC", 1),
                    GraphEdge("M-GRDVC", "M-GTN", 1),
                    GraphEdge("M-GTN", "M-RWT", 1),
                    GraphEdge("M-RWT", "M-LLRH", 1),
                    GraphEdge("M-LLRH", "M-MTJH", 1),
                    GraphEdge("M-MTJH", "M-CHNN", 1),
                    GraphEdge("M-CHNN", "M-NVN", 1),
                    GraphEdge("M-NVN", "M-BDC", 1),
                    GraphEdge("M-BDC", "M-NYG", 1),
                    GraphEdge("M-NYG", "M-KNP", 1)
                )

            9 -> listOf(
                GraphEdge("M-ALV", "M-PLN", 1),
                GraphEdge("M-PLN", "M-CMP", 1),
                GraphEdge("M-CMP", "M-AMB", 1),
                GraphEdge("M-AMB", "M-MTTM", 1),

                GraphEdge("M-MTTM", "M-KLM", 1),
                GraphEdge("M-KLM", "M-CCH", 1),
                GraphEdge("M-CCH", "M-PTHDP", 1),

                GraphEdge("M-PTHDP", "M-EDP", 1),
                GraphEdge("M-EDP", "M-CHNG", 1),
                GraphEdge("M-CHNG", "M-PLR", 1),

                GraphEdge("M-PLR", "M-JLNS", 1),
                GraphEdge("M-JLNS", "M-KLR", 1),

                GraphEdge("M-KLR", "M-TWN", 1),

                GraphEdge("M-TWN", "M-MGRDZ", 1),
                GraphEdge("M-MGRDZ", "M-MHRJ", 1),
                GraphEdge("M-MHRJ", "M-ERN", 1),

                GraphEdge("M-ERN", "M-KDV", 1),
                GraphEdge("M-KDV", "M-ELM", 1),
                GraphEdge("M-ELM", "M-VYT", 1),

                GraphEdge("M-VYT", "M-THY", 1),
                GraphEdge("M-THY", "M-PTTH", 1),
                GraphEdge("M-PTTH", "M-VDK", 1),

                GraphEdge("M-VDK", "M-SNJ", 1),
                GraphEdge("M-SNJ", "M-TRP", 1)
            )

            11

                -> listOf(
                GraphEdge("M-KKVSZ", "M-KSJR", 1),
                GraphEdge("M-KSJR", "M-KJNN", 1),
                GraphEdge("M-KJNN", "M-KKSK", 1),
                GraphEdge("M-KKSK", "M-KHMD", 1),

                GraphEdge("M-KJKA", "M-KTKP", 1),
                GraphEdge("M-KTKP", "M-KSKB", 1),
                GraphEdge("M-KSKB", "M-KBCR", 1),

                GraphEdge("M-KBCR", "M-KBBR", 1),
                GraphEdge("M-KBBR", "M-KTRT", 1),
                GraphEdge("M-KTRT", "M-KMJHZ", 1),

                GraphEdge("M-HWMM", "M-HWHM", 1),
                GraphEdge("M-HWHM", "M-MKNA", 1),
                GraphEdge("M-MKNA", "M-KESPZ", 1),

                GraphEdge("M-SVSA", "M-KESAZ", 1),
                GraphEdge("M-KESAZ", "M-CPSAZ", 1),
                GraphEdge("M-CPSAZ", "M-CCSCZ", 1),

                GraphEdge("M-CCSCZ", "M-BCSDZ", 1),
                GraphEdge("M-BCSDZ", "M-SSSAZ", 1),
                GraphEdge("M-SSSAZ", "M-PBGBZ", 1),

                GraphEdge("M-PBGBZ", "M-SDHMZ", 1),
                GraphEdge("M-KDSWZ", "M-KBAR", 1),
                GraphEdge("M-KBAR", "M-KNAPZ", 1),

                GraphEdge("M-KNAPZ", "M-KDMIZ", 1),
                GraphEdge("M-KDMIZ", "M-KBELZ", 1),
                GraphEdge("M-KBELZ", "M-KSHYZ", 1),

                GraphEdge("M-KSHYZ", "M-KSHOZ", 1),
                GraphEdge("M-KSHOZ", "M-KGPKZ", 1),
                GraphEdge("M-KGPKZ", "M-KMHRZ", 1),

                GraphEdge("M-KMHRZ", "M-KCENZ", 1),
                GraphEdge("M-KCENZ", "M-KCWCZ", 1),
                GraphEdge("M-KCWCZ", "M-KESPZ", 1),

                GraphEdge("M-KESPZ", "M-KPSKZ", 1),
                GraphEdge("M-KPSKZ", "M-KMDIZ", 1),
                GraphEdge("M-KMDIZ", "M-KRSDZ", 1),

                GraphEdge("M-KRSDZ", "M-KNBNZ", 1),
                GraphEdge("M-KNBNZ", "M-KJPKZ", 1),
                GraphEdge("M-KJPKZ", "M-KKHGZ", 1),

                GraphEdge("M-KKHGZ", "M-KRSBZ", 1),
                GraphEdge("M-KRSBZ", "M-KMUKZ", 1),
                GraphEdge("M-KMUKZ", "M-KNTJZ", 1),

                GraphEdge("M-KNTJZ", "M-KMSNZ", 1),
                GraphEdge("M-KMSNZ", "M-KGTNZ", 1),
                GraphEdge("M-KGTNZ", "M-KKNZ", 1),

                GraphEdge("M-KKNZ", "M-KSKDZ", 1),
                GraphEdge("M-KSKDZ", "M-KKVSZ", 1)
            )

            12

                -> listOf(
                GraphEdge("M-MNSHP", "M-INDRNGR", 1),
                GraphEdge("M-INDRNGR", "M-BHTN", 1),
                GraphEdge("M-BHTN", "M-LKHR", 1),
                GraphEdge("M-LKHR", "M-BDSH", 1),

                GraphEdge("M-BDSH", "M-ITCL", 1),
                GraphEdge("M-ITCL", "M-VSHV", 1),
                GraphEdge("M-VSHV", "M-KDSN", 1),

                GraphEdge("M-KDSN", "M-HZRTG", 1),
                GraphEdge("M-HZRTG", "M-SCH", 1),
                GraphEdge("M-SCH", "M-HSS", 1),

                GraphEdge("M-HSS", "M-CHRBG", 1),
                GraphEdge("M-CHRBG", "M-DRGPR", 1),

                GraphEdge("M-DRGPR", "M-MWYZ", 1),

                GraphEdge("M-MWYZ", "M-ALMBG", 1),
                GraphEdge("M-ALMBG", "M-ALMBGH", 1),
                GraphEdge("M-ALMBGH", "M-SNGRN", 1),

                GraphEdge("M-SNGRN", "M-KRSHNN", 1),
                GraphEdge("M-KRSHNN", "M-TRNSP", 1),
                GraphEdge("M-TRNSP", "M-AMSZZ", 1),

                GraphEdge("M-AMSZZ", "M-CCSR", 1)
            )

            14

                ->
                listOf(
                    GraphEdge("M-ANDH", "M-LWR", 1),

                    GraphEdge("M-LWR", "M-OSH", 1),
                    GraphEdge("M-OSH", "M-GRG", 1),
                    GraphEdge("M-GRG", "M-BNG", 1),

                    GraphEdge("M-BNG", "M-LWRM", 1),
                    GraphEdge("M-LWRM", "M-MLD", 1),
                    GraphEdge("M-MLD", "M-VLN", 1),

                    GraphEdge("M-VLN", "M-DHN", 1),
                    GraphEdge("M-DHN", "M-KNDV", 1),
                    GraphEdge("M-KNDV", "M-SHM", 1),

                    GraphEdge("M-SHM", "M-BRV", 1),
                    GraphEdge("M-BRV", "M-EKS", 1),
                    GraphEdge("M-EKS", "M-MND", 1),

                    GraphEdge("M-MND", "M-KNDR", 1),
                    GraphEdge("M-KNDR", "M-ANN", 1),
                    GraphEdge("M-ANN", "M-DHS", 1),

                    GraphEdge("M-DHS", "M-OVR", 1),
                    GraphEdge("M-OVR", "M-RSH", 1),
                    GraphEdge("M-RSH", "M-DVP", 1),

                    GraphEdge("M-DVP", "M-MGT", 1),
                    GraphEdge("M-MGT", "M-PSR", 1),
                    GraphEdge("M-PSR", "M-AKR", 1),

                    GraphEdge("M-AKR", "M-KRR", 1),
                    GraphEdge("M-KRR", "M-DND", 1),
                    GraphEdge("M-DND", "M-ARY", 1),

                    GraphEdge("M-ARY", "M-GRGN", 1),
                    GraphEdge("M-GRGN", "M-JGS", 1),
                    GraphEdge("M-JGS", "M-MGR", 1),

                    GraphEdge("M-MGR", "M-GND", 1),
                    GraphEdge("M-VRS", "M-DNN", 1),
                    GraphEdge("M-DNN", "M-AZD", 1),

                    GraphEdge("M-AZD", "M-AND", 1),
                    GraphEdge("M-AND", "M-WST", 1),
                    GraphEdge("M-WST", "M-CHK", 1),

                    GraphEdge("M-CHK", "M-ARP", 1),
                    GraphEdge("M-ARP", "M-MNK", 1),
                    GraphEdge("M-MNK", "M-SKN", 1),

                    GraphEdge("M-SKN", "M-ASL", 1),
                    GraphEdge("M-ASL", "M-JGR", 1),
                    GraphEdge("M-JGR", "M-GHT", 1),

                    GraphEdge("M-BLP", "M-RBC", 1),
                    GraphEdge("M-RBC", "M-BLPD", 1),
                    GraphEdge("M-BLPD", "M-UTS", 1),

                    GraphEdge("M-UTS", "M-KND", 1),
                    GraphEdge("M-KND", "M-KHR", 1),
                    GraphEdge("M-KHR", "M-CNT", 1),

                    GraphEdge("M-CNT", "M-PTH", 1),
                    GraphEdge("M-PTH", "M-AMN", 1),
                    GraphEdge("M-AMN", "M-PTHL", 1),

                    GraphEdge("M-PTHL", "M-PND", 1),
                    GraphEdge("M-SNTG", "M-LWRP", 1),
                    GraphEdge("M-LWRP", "M-MNTC", 1),

                    GraphEdge("M-MNTC", "M-AMBD", 1),
                    GraphEdge("M-AMBD", "M-NGN", 1),
                    GraphEdge("M-NGN", "M-DDR", 1),

                    GraphEdge("M-DDR", "M-WDL", 1),
                    GraphEdge("M-WDL", "M-ACH", 1),
                    GraphEdge("M-ACH", "M-ANT", 1),

                    GraphEdge("M-ANT", "M-GTBN", 1),
                    GraphEdge("M-GTBN", "M-WDLD", 1),
                    GraphEdge("M-WDLD", "M-BHKT", 1),

                    GraphEdge("M-BHKT", "M-MYSR", 1),
                    GraphEdge("M-MYSR", "M-BHRT", 1),
                    GraphEdge("M-BHRT", "M-FRT", 1),

                    GraphEdge("M-FRT", "M-VNP", 1),
                    GraphEdge("M-VNP", "M-CHM", 1),
                    GraphEdge("M-ACA", "M-WOR", 1),

                    GraphEdge("M-WOR", "M-SIDV", 1),
                    GraphEdge("M-SIDV", "M-DDRM", 1),
                    GraphEdge("M-DDRM", "M-SDIT", 1),

                    GraphEdge("M-SDIT", "M-DHAV", 1),
                    GraphEdge("M-DHAV", "M-BDRM", 1),
                    GraphEdge("M-BDRM", "M-VIDN", 1),

                    GraphEdge("M-VIDN", "M-STZM", 1),
                    GraphEdge("M-STZM", "M-CIAD", 1),
                    GraphEdge("M-CIAD", "M-SHRR", 1),

                    GraphEdge("M-SHRR", "M-CSIA", 1),
                    GraphEdge("M-CSIA", "M-MNK", 1),
                    GraphEdge("M-MNK", "M-MIDC", 1),

                    GraphEdge("M-MIDC", "M-SEPZ", 1),
                    GraphEdge("M-SEPZ", "M-ARYJ", 1),
                    GraphEdge("M-ANDH", "M-DNN", 0),

                    GraphEdge("M-GND", "M-WST", 0)
                )

            15

                -> listOf(
                GraphEdge("M-ATM", "M-NRRD", 1),
                GraphEdge("M-NRRD", "M-KDBS", 1),
                GraphEdge("M-KDBS", "M-GDDG", 1),
                GraphEdge("M-GDDG", "M-KSTR", 1),

                GraphEdge("M-KSTR", "M-ZRML", 1),
                GraphEdge("M-ZRML", "M-STBLDZ", 1),
                GraphEdge("M-STBLDZ", "M-CNGR", 1),

                GraphEdge("M-CNGR", "M-RHTC", 1),
                GraphEdge("M-RHTC", "M-AJNS", 1),
                GraphEdge("M-AJNS", "M-CHHTRP", 1),

                GraphEdge("M-CHHTRP", "M-JPRK", 1),
                GraphEdge("M-JPRK", "M-UJWL", 1),

                GraphEdge("M-UJWL", "M-ARPRTZ", 1),

                GraphEdge("M-ARPRTZ", "M-ARPRTS", 1),
                GraphEdge("M-ARPRTS", "M-NWRP", 1),
                GraphEdge("M-NWRP", "M-KHPR", 1),

                GraphEdge("M-LKMN", "M-BNSNG", 1),
                GraphEdge("M-BNSNG", "M-VSDV", 1),
                GraphEdge("M-VSDV", "M-RCHN", 1),

                GraphEdge("M-RCHN", "M-SBHS", 1),
                GraphEdge("M-SBHS", "M-DHRM", 1),
                GraphEdge("M-DHRM", "M-LDSQ", 1),

                GraphEdge("M-LDSQ", "M-SHNKR", 1),
                GraphEdge("M-SHNKR", "M-INST", 1),
                GraphEdge("M-INST", "M-JHNSR", 1),

                GraphEdge("M-JHNSR", "M-STBLDZ", 1),
                GraphEdge("M-STBLDZ", "M-CTTN", 1),
                GraphEdge("M-CTTN", "M-NGPR", 1),

                GraphEdge("M-NGPR", "M-DSRVS", 1),
                GraphEdge("M-DSRVS", "M-AGRS", 1),
                GraphEdge("M-AGRS", "M-CHTRL", 1),

                GraphEdge("M-CHTRL", "M-TLPH", 1),
                GraphEdge("M-TLPH", "M-AMBDKR", 1),
                GraphEdge("M-AMBDKR", "M-VSHND", 1),

                GraphEdge("M-VSHND", "M-PRJP", 1)
            )

            16 -> listOf(
                GraphEdge("M-SWR", "M-MNDZ", 1),
                GraphEdge("M-MNDZ", "M-KSB", 1),
                GraphEdge("M-KSB", "M-CVLC", 1),
                GraphEdge("M-CVLC", "M-SHVJN", 1),

                GraphEdge("M-SHVJN", "M-BPD", 1),
                GraphEdge("M-BPD", "M-DPD", 1),
                GraphEdge("M-DPD", "M-PHG", 1),

                GraphEdge("M-PHG", "M-KSR", 1),
                GraphEdge("M-KSR", "M-BHS", 1),
                GraphEdge("M-BHS", "M-SNTT", 1),

                GraphEdge("M-SNTT", "M-PCM", 1),
                GraphEdge("M-VNZ", "M-ANNDN", 1),
                GraphEdge("M-ANNDN", "M-IDL", 1),

                GraphEdge("M-IDL", "M-NLS", 1),
                GraphEdge("M-NLS", "M-GRW", 1),
                GraphEdge("M-GRW", "M-DCC", 1),

                GraphEdge("M-DCC", "M-CHHT", 1),
                GraphEdge("M-CHHT", "M-PNM", 1),
                GraphEdge("M-PNM", "M-CVLCR", 1),

                GraphEdge("M-CVLCR", "M-MNG", 1),
                GraphEdge("M-MNG", "M-PNR", 1),
                GraphEdge("M-PNR", "M-RBH", 1),

                GraphEdge("M-RBH", "M-BND", 1),
                GraphEdge("M-BND", "M-YRW", 1),
                GraphEdge("M-YRW", "M-KLY", 1),

                GraphEdge("M-KLY", "M-RMW", 1),
                GraphEdge("M-CVLC", "M-CVLCR", 0)
            )

            else -> emptyList()
        }
    }

    private fun getCity(): String {
        return when (topologyId) {
            0 -> City.Ahmedabad.name
            1 -> City.Agra.name
            2 -> City.Bengaluru.name
            3 -> City.Delhi.name
            4 -> City.Chennai.name
            6 -> City.Hyderabad.name
            7 -> City.Jaipur.name
            8 -> City.Kanpur.name
            9 -> City.Kochi.name
            11 -> City.Kolkata.name
            12 -> City.Lucknow.name
            14 -> City.Mumbai.name
            15 -> City.Nagpur.name
            16 -> City.Pune.name
            else -> "Unknown"
        }
    }
}