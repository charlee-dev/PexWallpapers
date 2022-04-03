package com.adwi.feature_settings.ui.privacy

data class PrivacyCategory(
    val name: String,
    val items: List<PrivacyItem>
)

data class PrivacyItem(
    val title: String,
    val description: String
)

val privacyCategoryList = listOf(
    PrivacyCategory(
        name = "Collection of Your information",
        items = listOf(
            PrivacyItem(
                title = "Personal Data",
                description = "Demographic and other personally identifiable information (such as your name and email" +
                        "address) that you voluntarily give to us when choosing to participate in various activities related" +
                        "to the Application, such as chat, posting messages in comment sections or in our forums, liking" +
                        "posts, sending feedback, and responding to surveys. If you choose to share data about yourself" +
                        "via your profile, online chat, or other interactive areas of the Application, please be advised that" +
                        "all data you disclose in these areas is public and your data will be accessible to anyone who" +
                        "accesses the Application."
            ),
            PrivacyItem(
                title = "Derivative Data",
                description = "Information our servers automatically collect when you access the Application, such as your" +
                        "native actions that are integral to the Application, including liking, re-blogging, or replying to a" +
                        "post, as well as other interactions with the Application and other users via server log files. "
            ),
            PrivacyItem(
                title = "Financial Data",
                description = "Not applicable for VDLNedcar apps \n" +
                        "\nThere is no payment necessary for this Application. Financial information, such as data related\n" +
                        "to your payment method is for this application Not Applicable."
            ),
            PrivacyItem(
                title = "Facebook Permissions",
                description = "Not applicable for VDLNedcar apps"
            ),
            PrivacyItem(
                title = "Data from Social Networks",
                description = "Not applicable for VDLNedcar apps \n" +
                        "\nUser information from social networking sites, such as [Apple’s Game Center, Facebook," +
                        "Google+ Instagram, Pinterest, Twitter], including your name, your social network username," +
                        "location, gender, birth date, email address, profile picture, and public data for contacts, if you" +
                        "connect your account to such social networks. This information may also include the contact" +
                        "information of anyone you invite to use and/or join the Application."
            ),
            PrivacyItem(
                title = "Geo-Location Information",
                description = "We may request access or permission to and track location-based information from your mobile" +
                        "device, either continuously or while you are using the Application, to provide location-based" +
                        "services. If you wish to change our access or permissions, you may do so in your device’s" +
                        "settings."
            ),
        )

    ),
    PrivacyCategory(
        name = "Disclosure of Your information",
        items = listOf(
            PrivacyItem(
                title = "By Law or to Protect Rights",
                description = "If we believe the release of information about you is necessary to respond to legal process, to" +
                        "investigate or remedy potential violations of our policies, or to protect the rights, property, and" +
                        "safety of others, we may share your information as permitted or required by any applicable law," +
                        "rule, or regulation. This includes exchanging information with other entities for fraud protection" +
                        "and credit risk reduction."
            ),
            PrivacyItem(
                title = "Third-Party Service Providers",
                description = "Not applicable for VDLNedcar apps \n" +
                        "\nWe may share your information with third parties that perform services for us or on our behalf," +
                        "including payment processing, data analysis, email delivery, hosting services, customer service," +
                        "and marketing assistance."
            ),
            PrivacyItem(
                title = "Marketing Communications",
                description = "Not applicable for VDLNedcar apps \n" +
                        "\nWith your consent, or with an opportunity for you to withdraw consent, we may share your" +
                        "information with third parties for marketing purposes, as permitted by law."
            )
        )
    )
)