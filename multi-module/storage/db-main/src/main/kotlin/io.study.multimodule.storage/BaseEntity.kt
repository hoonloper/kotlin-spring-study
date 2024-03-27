import java.time.ZonedDateTime


@MappedSuperclass
internal abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    val createdAt: ZonedDateTime? = null

    @UpdateTimestamp
    val updatedAt: ZonedDateTime? = null
        protected set
}