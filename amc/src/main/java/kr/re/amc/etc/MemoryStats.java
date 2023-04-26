package kr.re.amc.etc;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MemoryStats {

    private Long heapSize;
    private Long heapMaxSize;
    private Long heapFreeSize;

}
